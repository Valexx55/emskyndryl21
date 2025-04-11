package edu.kyndryl.academy.msalumnosprofe.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.kyndryl.academy.msalumnosprofe.client.ClienteFeignCurso;
import edu.kyndryl.academy.msalumnosprofe.service.AlumnoService;
import edu.kyndryl.academy.mscomunprofe.entity.Alumno;
import edu.kyndryl.academy.mscomunprofe.entity.Curso;
import edu.kyndryl.academy.mscomunprofe.entity.FraseChiquito;
//import edu.kyndryl.academy.mscomunprofe.entity.FraseChiquito;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

/**
 * esta capa recibe y contesta las peticiones del cliente
 * 
 * en esta clase exhibimos las operaciones públicas accesibles a través de una
 * URL / API WEB
 * 
 * RESTfull Alumnos
 * 
 * ALTA - POST BAJA - DELETE MODIFICACIÓN - PUT CONSULTA - GET
 * 
 * 
 */
//@Controller //sería si la capa de controller devuelve vistas (HTML, JSP)
@CrossOrigin(originPatterns = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT  })
@RestController // le estoy diciendo que trabajo con JSON - de modo que si yo devuelvo un alumno
				// , Spring automáticamente lo convierte a JSON
@RequestMapping("/alumno") // especifo que las peticiones a esta clase serán del tipo
							// http://dominio:puerto/alumno
public class AlumnoController {

	@Autowired
	AlumnoService alumnoService;

	@Autowired
	ClienteFeignCurso clienteFeignCurso;

	@Value("${instancia}")
	String nombre_instancia;
	
	@Value("${mientorno}")
	String mientorno;

	@Autowired
	Environment environment;

	Logger logger = LoggerFactory.getLogger(AlumnoController.class);// dame un log para esta clase

	
	@PostConstruct
	public void postCreacion ()
	{
		logger.debug("EN POST CONSTRUCT " + mientorno);
	}
	
	// yo a Spring le digo oye: si recibes un GET
	// localhost:8081/alumno/obtener-alumno-test
	// invocas a este método
	@GetMapping("/obtener-alumno-test")
	public Alumno obtenerAlumnoTest() {
		Alumno alumno = null;

		// alumno.getApellido(); //nullPointer
		logger.debug("Entrando en obtenerAlumnoTest");
		alumno = new Alumno("Laura", 60, "Gómez", "laura@kyndryl.com");
		logger.debug("Alumno " + alumno.toString());
		logger.debug("Final obtenerAlumnoTest");
		alumno.getNombre().charAt(5);
		// TODO COMENTAR TOSTRING EN ENTITADES Y BEANS LOG

		return alumno;// SERIALIZA Objeto de Java - A String JSON
	}

	// * RESTfull

	// * BAJA - DELETE
	// * MODIFICACIÓN - PUT
	// * CONSULTA un alumno - GET

	// CONSULTA DE TODOS LOS ALUMNOS
	// ResponseEntity<Tipo que va en el cuerpo> es la clase que envuelve/representa
	// el mensaje HTTP de Respuesta

	// @GetMapping("/") //GET localhost:8081/alumno
	@GetMapping
	public ResponseEntity<Iterable<Alumno>> listarAlumnos() {
		ResponseEntity<Iterable<Alumno>> responseEntity = null;

		logger.debug("EN listarAlumnos()");
		logger.debug("Nombre instancia = " + this.nombre_instancia + " PUERTO "
				+ environment.getProperty("local.server.port"));
		try {
			logger.debug("HOSTNAME " + InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Iterable<Alumno> listadoAlumnos = alumnoService.consultarTodos();
		responseEntity = ResponseEntity.ok(listadoAlumnos);// genero el mensaje de respuesta en la cabecera ok 200 y en
															// el cuerpo, el listado de alumnos
		logger.debug("Listado de alumnos " + listadoAlumnos.toString());

		return responseEntity;
	}

	// * CONSULTA un alumno - GET

	@Operation(summary = "", description = "Este método, nos recupera un alumno dado un id", tags = {
			"alumno-controller" })
	@GetMapping("/{id}") // GET localhost:8081/alumno/5 //@PathVariable carga el valor
	public ResponseEntity<Alumno> listarAlumnoPorId(@PathVariable Long id) {
		ResponseEntity<Alumno> responseEntity = null;
		Alumno alumnoLeido = null;

		logger.debug("EN listarAlumnoPorId()");
		Optional<Alumno> oAlumno = alumnoService.consultarPorId(id);
		if (oAlumno.isPresent()) {
			alumnoLeido = oAlumno.get();
			responseEntity = ResponseEntity.ok(alumnoLeido);
			logger.debug("Alumno leído " + alumnoLeido);
		} else {
			logger.debug("Alumno no encontrado con id " + id);
			responseEntity = ResponseEntity.noContent().build();
		}

		return responseEntity;
	}

	// * BAJA - DELETE
	@DeleteMapping("/{id}") // DELETE localhost:8081/alumno
	public ResponseEntity<Void> borrarAlumnoPorId(@PathVariable Long id) {
		ResponseEntity<Void> responseEntity = null;

		logger.debug("EN borrarAlumnoPorId()");
		alumnoService.borrarPorId(id);
		responseEntity = ResponseEntity.ok().build();

		return responseEntity;
	}

	private ResponseEntity<?> obtenerErrores(BindingResult br) {
		ResponseEntity<?> responseEntity = null;

		logger.debug("El alumno trae fallos");
		List<ObjectError> listaErrores = br.getAllErrors();
		listaErrores.forEach((ObjectError error) -> // esta función en realidad es accept
		{
			logger.error(error.toString());
		});

		responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listaErrores);
		return responseEntity;
	}

	// POST - INSERTAR
	// @PostMapping("/") //POST localhost:8081/alumno
	@PostMapping // POST localhost:8081/alumno
	public ResponseEntity<?> insertarAlumno(@Valid @RequestBody Alumno alumno, BindingResult br) {
		ResponseEntity<?> responseEntity = null;
		Alumno alumnoInsertado = null;

		logger.debug("EN insertarAlumno()");
		if (br.hasErrors()) {
			logger.debug("Error en los datos de entrada");
			responseEntity = obtenerErrores(br);
		} else {

			logger.debug("Validación OK");
			alumnoInsertado = alumnoService.alta(alumno);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumnoInsertado);
			logger.debug("Alumno insertado = " + alumnoInsertado);

		}

		return responseEntity;
	}

	@PostMapping("/insertar-alumno-foto") // POST localhost:8081/alumno
	public ResponseEntity<?> insertarAlumnoConFoto(@Valid Alumno alumno, BindingResult br, MultipartFile archivo)
			throws IOException {
		ResponseEntity<?> responseEntity = null;
		Alumno alumnoInsertado = null;

		logger.debug("EN insertarAlumno()");
		if (br.hasErrors()) {
			logger.debug("Error en los datos de entrada");
			responseEntity = obtenerErrores(br);
		} else {

			logger.debug("Validación OK");
			try {
				if (!archivo.isEmpty()) {
					alumno.setFoto(archivo.getBytes());
				}
			} catch (Exception e) {

				logger.error("Error al acceder a la foto", e);
				throw e;
			}

			alumnoInsertado = alumnoService.alta(alumno);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumnoInsertado);
			logger.debug("Alumno insertado = " + alumnoInsertado);

		}

		return responseEntity;
	}
	
	
	@GetMapping("/obtenerFoto/{id}") 
	public ResponseEntity<Resource> obtenerFotoAlumnoPorId(@PathVariable Long id) {
		ResponseEntity<Resource> responseEntity = null;
		Alumno alumnoLeido = null;
		Resource imagen = null;

		logger.debug("EN listarAlumnoPorId()");
		Optional<Alumno> oAlumno = alumnoService.consultarPorId(id);
		if (oAlumno.isPresent() && oAlumno.get().getFoto()!=null) {
			alumnoLeido = oAlumno.get();
			imagen =  new ByteArrayResource(alumnoLeido.getFoto());
			responseEntity = ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
			logger.debug("Alumno leído " + alumnoLeido);
		} else {
			logger.debug("Alumno no encontrado con id " + id);
			responseEntity = ResponseEntity.noContent().build();
		}

		return responseEntity;
	}
	
	
	
	
	
	

	@PutMapping("/{id}") // PUT localhost:8081/alumno/3
	public ResponseEntity<Alumno> modificarAlumno(@RequestBody Alumno alumno, @PathVariable Long id) {
		ResponseEntity<Alumno> responseEntity = null;
		Alumno alumnoModificado = null;

		logger.debug("EN modificarAlumno()");
		Optional<Alumno> oAlumno = alumnoService.modificarAlumnoPorId(alumno, id);
		if (oAlumno.isPresent()) {
			alumnoModificado = oAlumno.get();
			responseEntity = ResponseEntity.ok(alumnoModificado);
			logger.debug("Alumno leído " + alumnoModificado);
		} else {
			logger.debug("Alumno no encontrado con id " + id);
			responseEntity = ResponseEntity.notFound().build();
		}

		return responseEntity;
	}
	
	
	@PutMapping("/editar-con-foto/{id}") // PUT localhost:8081/alumno/3
	public ResponseEntity<Alumno> modificarAlumnoConFoto(Alumno alumno, MultipartFile archivo  ,@PathVariable Long id) throws IOException {
		ResponseEntity<Alumno> responseEntity = null;
		Alumno alumnoModificado = null;

		logger.debug("EN modificarAlumno()");
		if (!archivo.isEmpty())
		{
			try {
				alumno.setFoto(archivo.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Error al tratar el archivo adjunto", e);
				throw e;
			}
		}
		Optional<Alumno> oAlumno = alumnoService.modificarAlumnoPorId(alumno, id);
		if (oAlumno.isPresent()) {
			alumnoModificado = oAlumno.get();
			responseEntity = ResponseEntity.ok(alumnoModificado);
			logger.debug("Alumno leído " + alumnoModificado);
		} else {
			logger.debug("Alumno no encontrado con id " + id);
			responseEntity = ResponseEntity.notFound().build();
		}

		return responseEntity;
	}
	
	
	
	
	//GET FOTO id --> foto

	@GetMapping("/listarAlumnosPorRangoEdad") // GET localhost:8081/alumno?edadmin=10&edadmax=20
	public ResponseEntity<Iterable<Alumno>> listarAlumnosPorRangoEdad(
			@RequestParam(name = "edadmin", required = true) int edadmin,
			@RequestParam(name = "edadmax", required = true) int edadmax) {
		ResponseEntity<Iterable<Alumno>> responseEntity = null;

		logger.debug("EN listarAlumnosPorRangoEdad()");
		Iterable<Alumno> listadoAlumnos = alumnoService.buscarAlumnosPorIntervaloDeEdad(edadmin, edadmax);
		responseEntity = ResponseEntity.ok(listadoAlumnos);
		logger.debug("listado alumnos " + listadoAlumnos);

		return responseEntity;
	}
	
	@GetMapping("/listarAlumnosPorRangoEdadPaginado") // GET localhost:8081/listarAlumnosPorRangoEdadPaginado?edadmin=10&edadmax=20&page=0&size=2
	public ResponseEntity<Iterable<Alumno>> listarAlumnosPorRangoEdadPaginado(
			@RequestParam(name = "edadmin", required = true) int edadmin,
			@RequestParam(name = "edadmax", required = true) int edadmax,
			Pageable pageable) {
		ResponseEntity<Iterable<Alumno>> responseEntity = null;

		logger.debug("EN listarAlumnosPorRangoEdad()");
		Iterable<Alumno> listadoAlumnos = alumnoService.findByEdadBetween(edadmin, edadmax, pageable);
		responseEntity = ResponseEntity.ok(listadoAlumnos);
		logger.debug("listado alumnos " + listadoAlumnos);

		return responseEntity;
	}

	@GetMapping("/obtenerFraseChiquito") // GET localhost:8081/alumno/obtenerFraseChiquito
	public ResponseEntity<FraseChiquito> obtenerFraseChiquito() {
		ResponseEntity<FraseChiquito> responseEntity = null;
		FraseChiquito fraseChiquito = null;

		logger.debug("EN obtenerFraseChiquito()");
		Optional<FraseChiquito> oFrase = alumnoService.obtenerFraseAleatoria();
		if (oFrase.isPresent()) {
			fraseChiquito = oFrase.get();
			responseEntity = ResponseEntity.ok(fraseChiquito);
			logger.debug("Fase obtenida " + fraseChiquito);
		} else {
			logger.debug("Frase no recibida ");
			responseEntity = ResponseEntity.noContent().build();
		}

		return responseEntity;
	}

	// obtenerCursoAlumno: recibo un id de alumno y devuelvo el curso en que está
	// matriculado
	@GetMapping("/obtener-curso-alumno-via-feign/{idalumno}")
	public ResponseEntity<?> obtenerCursoAlumnoViaFeign(@PathVariable Long idalumno) {
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> o_curso = Optional.empty();

		o_curso = this.clienteFeignCurso.obtenerCursoAlumno(idalumno);
		// mostrarCabeceras(params);

		if (o_curso.isPresent()) {
			Curso curso_modificado = o_curso.get();
			responseEntity = ResponseEntity.ok(curso_modificado);
		} else {
			// no había un curso con ese ID
			responseEntity = ResponseEntity.noContent().build();// 204
		}

		return responseEntity;

	}
	
	
	@GetMapping("/pagina") //GET /pagina?page=1&size=3&sort=edad,ASC
	public ResponseEntity<Iterable<Alumno>> listarAlumnosPorPagina(Pageable pageable) {
		ResponseEntity<Iterable<Alumno>> responseEntity = null;

		logger.debug("listarAlumnosPorPagina");
			

		Iterable<Alumno> listadoAlumnos = alumnoService.consultarAlumnosPorPagina(pageable);
		responseEntity = ResponseEntity.ok(listadoAlumnos);
		logger.debug("Listado de alumnos por página " + listadoAlumnos.toString());

		return responseEntity;
	}

	
	
	
	
	
	
	
	
	
	
	

}
