# Ecosistema de Microservicios para E-commerce

Este proyecto implementa una arquitectura de microservicios basada en Spring Boot y Spring Cloud, simulando las funcionalidades básicas de una plataforma de e-commerce. La arquitectura está diseñada para ser resiliente, escalable y mantenible.

## Arquitectura y Componentes

El ecosistema está compuesto por los siguientes servicios, cada uno con su propia base de datos PostgreSQL:

*   **Eureka Server:** Para el registro y descubrimiento de servicios.
*   **API Gateway:** Como único punto de entrada al sistema.
*   **Microservicio `producto`:** Gestiona los productos.
*   **Microservicio `carrito`:** Gestiona los carritos de compra.
*   **Microservicio `venta`:** Gestiona las ventas.

### Repositorio Relacionado

**`config-server-ms`**: Este repositorio contiene la aplicación **Spring Cloud Config Server**, responsable de distribuir la configuración centralizada a todos los microservicios.

## Características Técnicas

*   **Comunicación:** Realizada con **Spring Cloud OpenFeign**.
*   **Balanceo de Carga:** Se utiliza **Spring Cloud Load Balancer** para distribuir las peticiones entre las instancias disponibles de un servicio.
*   **Resiliencia:** Implementación del patrón **Circuit Breaker** con **Resilience4j**.
*   **Persistencia:** Realizada con **Spring Data JPA** y Hibernate.

## Cómo Ejecutar

1.  Clonar todos los repositorios necesarios.
2.  Configurar las variables de entorno para la base de datos.
3.  Iniciar los servicios en el siguiente orden: Config Server, Eureka Server, y luego los demás microservicios de negocio y el API Gateway.




