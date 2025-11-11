package contracts.carrito

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    // Nombre descriptivo de la interacción
    description "Debería devolver los detalles de un carrito cuando existe"

    request {
        // Petición que el consumidor (venta) hará
        method GET()
        url '/carritos/15'
    }

    response {
        // Respuesta que el proveedor (carrito) debe devolver
        status 200
        headers {
            contentType(applicationJson())
        }
        body([
            idCarrito: 15,
            precioTotal: 1250.99,
            productos: [
                [
                    idProducto: 1,
                    nombre: "Laptop Gamer",
                    marca: "Asus",
                    precio: 1250.99
                ]
            ]
        ])
    }
}