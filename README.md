# ProyectoFinalEquipoF
En este repositorio vamos a crear dos ramas,  main donde me voy a encargar de realizar de pushear (solo yo) y otra rama Developer, en la todos los developers van a realizar el push de los cambios con el trabajo realizado.

DESCRIPCIÓN DEL PROYECTO
Es una aplicación web que proporciona herramientas tanto a inmobiliaria o dueño de un inmueble como también a inquilinos que estén alquilando un inmueble bajo la gestión de “MrHouse”. Es una plataforma donde se podrán encontrar ofertas de casas, departamentos, oficinas o locales cerca de su ubicación. Pero además, nos gustaría darle a los inquilinos la posibilidad de organizar reuniones, reclamos, recordatorios y descarga de boletas de pago de alquiler o impuestos.

PROBLEMA
Encontrar un sitio donde poder observar todas las ofertas de inmuebles de tu ciudad puede ser realmente estresante, tanto si buscás un espacio donde iniciar tu nuevo hogar, como una oficina o un lugar para vacacionar. Además, ya teniendo un lugar, se vuelve muy tedioso acordarse de fechas de pagos de alquiler, impuestos e informar a los administradores acerca de alguna situación a resolver.

SOLUCIÓN
Tener un solo lugar donde buscar o gestionar la administración de tu inmueble, ya seas dueño, empresa inmobiliaria o inquilino.

AUDIENCIA
Esta aplicación está dirigida a empresas inmobiliarias, dueños, futuros compradores o inquilinos.

CASOS DE USO
1- Los dueños o inmobiliarias podrán gestionar el sector de ofertas, responder ofertas de compras, gestionar reuniones con compradores o inquilinos (MVP).
2- Los compradores podrán realizar consultas, ofertas, solicitar citas para reunirse con dueños o inmobiliarias (MVP).
3- Los inquilinos podrán solicitar citas para reunirse con dueños o inmobiliarias, generar reclamos, descargar impuestos o boletas de pago del alquiler o comprobantes de pago.

REQUERIMIENTOS TÉCNICOS A CUMPLIR
Requerimientos obligatorios:
1- Registro y Login con Spring Security
2- Crear al menos DOS roles distintos para los usuarios.
3- Incluir tabla html en alguna vista
4- Carga y actualización de imagen
5- Crear una Query de búsqueda personalizada
6- Crear un CRUD
7- Que haya al menos un formulario.
8- Crear al menos 3 vistas distintas.
9- Diagrama UML de entidades

Requerimientos optativos
1- Motor de búsqueda
2- Tabla con opciones de agregar / modificar y eliminar registros
3- Utilizar atributos booleanos de alta y baja de usuarios (y poder modificarlos)
4- Listado en tabla por filtro (por nombre, fechas, altas o bajas, etc)
5- Generar por lo menos 5 vistas distintas que implementen th:fragments.
6- Generar un dashboard de administración de la app (el rol Administrador tendrá acceso a información que un rol User o Guest no tendría)
7- Aplicar principios de código limpio y buenas prácticas.
8- Añadir diagrama de casos de uso en UML

Requerimientos específicos de este proyecto:
1- Un GUEST (público) puede ver publicaciones de inmuebles disponibles para compra o alquiler, pero no puede ofertar ni reservar turnos.
2- Un CLIENT puede registrarse y modificar sus datos personales, excepto nombre y DNI. Solo podrá ver desde su perfil los inmuebles adquiridos a través de la app o gestionados por un ENTE a través de la app.
3- Un CLIENT puede ver otros inmuebles disponibles y ofertar o solicitar turnos.
4- Un CLIENT puede también recibir su perfil por medio del ENTE gestor del inmueble. Si no existe, lo crea. Si el DNI coincide con un CLIENT ya registrado, se vincula este con el ENTE y la propiedad solicitada a través del formulario.
5- Un CLIENT puede solicitar el rol de ENTE y un ENTE puede solicitar el rol CLIENT.
Ninguno puede ofertar para sus propios inmuebles.
6- Un ENTE puede registrarse, publicar, modificar, eliminar y ver sus inmuebles.
7- Un ENTE puede modificar las ofertas asociadas a sus inmuebles, aceptar o rechazar ofertas de usuarios CLIENT. Si acepta la propiedad se asocia al correspondiente CLIENT, pero ambos deben confirmar transferencia antes de que se desvincule de ENTE y pase a las propiedades de CLIENT.
8- Un ENTE puede publicar, modificar, eliminar horarios disponibles para reservas en su calendario por publicación.
9- Un CLIENT puede reservar turnos en un calendario de ENTE por publicación.
10- Un ADMIN puede otorgar, modificar y revocar roles. También puede dar de alta o
baja y listar CLIENT y ENTE.
11- Un ADMIN puede dar de alta o baja publicaciones.
12- Idealmente la app se puede expandir para permitir gestionar archivos (comprobantes de pagos, impuestos y recibos de alquiler) y además iniciar ‘incidencias’ donde los inquilinos puedan levantar un reclamo sobre la propiedad en alquiler con un pequeño detalle y ENTE pueda marcar la incidencia como cerrada una vez que se resuelva y escribir un pequeño detalle. Sin embargo, esto no entra dentro del MVP y no es obligatorio ni requerido.
