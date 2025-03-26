document.addEventListener("DOMContentLoaded", function() {
    const inputBuscar = document.querySelector(".buscar-container input");
    const tablaCoches = document.querySelector(".mostrarConsulta-container table tbody");

    // Cargar los coches desde el backend
    fetch("/gestionar/datos")
        .then(response => response.json())
        .then(coches => {
            if (coches.error) {
                console.error("Error:", coches.error);
                return;
            }

            // Agregar coches a la tabla
            coches.forEach(coche => {
                const fila = document.createElement("tr");
                fila.innerHTML = `
                    <td class="cod">${coche.cod}</td>
                    <td class="marca">${coche.marca}</td>
                    <td class="modelo">${coche.modelo}</td>
                    <td class="fecha">${coche.fecha}</td>
                    <td class="matricula">${coche.matricula}</td>
                    <td class="chasis">${coche.numChasis}</td> <!-- Cambié 'numChasis' por 'chasis' -->
                    <td>
                        <div class="botones-container">
                            <button class="btn-modificar"><i class="fas fa-edit"></i> Modificar</button>
                            <button class="btn-eliminar"><i class="fas fa-trash"></i> Eliminar</button>
                        </div>
                    </td>
                `;
                tablaCoches.appendChild(fila);

                // Añadir la funcionalidad de "Modificar"
                fila.querySelector(".btn-modificar").addEventListener("click", function() {
                    // Obtener las celdas de la fila
                    const celdas = fila.querySelectorAll("td:not(:first-child):not(:last-child)"); // Excluye "cod" y la columna de acciones

                    // Si ya estamos en modo edición, no hacer nada
                    if (fila.classList.contains("editando")) return;

                    // Agregar clase de edición para evitar modificaciones repetidas
                    fila.classList.add("editando");

                    // Convertir las celdas en inputs para la edición
                    celdas.forEach(celda => {
                        const valor = celda.textContent;
                        celda.innerHTML = `<input type="text" value="${valor}">`;
                    });

                    // Cambiar el texto y funcionalidad del botón de "Modificar"
                    const btnModificar = fila.querySelector(".btn-modificar");
                    btnModificar.innerHTML = '<i class="fas fa-save"></i> Guardar';
                    btnModificar.classList.remove("btn-modificar");
                    btnModificar.classList.add("btn-guardar");

                    // Cuando se hace clic en "Guardar"
                    btnModificar.addEventListener("click", function() {
                        // Obtener los valores de los inputs
                        const nuevosValores = Array.from(fila.querySelectorAll("td input")).map(input => input.value);

                        // Validación de campos vacíos
                        if (nuevosValores.some(valor => valor.trim() === "")) {
                            alert("Todos los campos deben estar completos.");
                            return;
                        }

                        // Crear un objeto con los nuevos valores
                        const datosModificados = {
                            cod: fila.querySelector(".cod").textContent, // El cod no debe cambiar
                            marca: nuevosValores[0],
                            modelo: nuevosValores[1],
                            fecha: nuevosValores[2],
                            matricula: nuevosValores[3],
                            numChasis: nuevosValores[4]  // Cambié numChasis por chasis
                        };

                        // Enviar los datos actualizados al backend
                        fetch("/gestionar/actualizar", {
                            method: "POST",
                            headers: {
                                "Content-Type": "application/json",
                            },
                            body: JSON.stringify(datosModificados)
                        })
                        .then(response => response.json())
                        .then(data => {
                            if (data.success) {
                                // Actualizar la tabla con los nuevos valores
                                celdas.forEach((celda, index) => {
                                    celda.innerHTML = nuevosValores[index];
                                });

                                // Cambiar el botón de "Guardar" de vuelta a "Modificar"
                                btnModificar.innerHTML = '<i class="fas fa-edit"></i> Modificar';
                                btnModificar.classList.remove("btn-guardar");
                                btnModificar.classList.add("btn-modificar");

                                // Eliminar la clase de edición
                                fila.classList.remove("editando");
                            } else {
                                console.error("Error al actualizar los datos:", data);
                                alert("Error al actualizar los datos.");
                            }
                        })
                        .catch(error => {
                            console.error("Error al enviar los datos:", error);
                            alert("Error al enviar los datos.");
                        });
                    });
                });
            });

            // Filtrar coches mientras se escribe
            inputBuscar.addEventListener("input", function() {
                const filtro = inputBuscar.value.toLowerCase().trim();

                document.querySelectorAll(".mostrarConsulta-container table tbody tr").forEach(fila => {
                    let mostrar = false;

                    // Si el filtro tiene ":", se intenta buscar en una columna específica
                    if (filtro.includes(":")) {
                        const [clave, valorFiltro] = filtro.split(":").map(str => str.trim());

                        // Verifica si la clave es válida y filtra
                        if (["cod", "marca", "modelo", "fecha", "matricula", "chasis"].includes(clave)) {
                            const valorCelda = fila.querySelector(`.${clave}`)?.textContent.toLowerCase();

                            if (valorCelda) {
                                mostrar = valorCelda.includes(valorFiltro);
                            } else {
                                mostrar = false;
                            }
                        }
                    } else {
                        // Búsqueda general en toda la fila
                        const valoresFila = Array.from(fila.querySelectorAll("td")).map(td => td.textContent.toLowerCase());
                        mostrar = valoresFila.some(valor => valor.includes(filtro));
                    }

                    fila.style.display = mostrar ? "" : "none";
                });
            });
        })
        .catch(error => console.error("Error al cargar los coches:", error));
});
