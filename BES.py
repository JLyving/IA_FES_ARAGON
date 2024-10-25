import csv

# Función para leer la lista de adyacencia desde el archivo CSV
def leer_lista_adyacencia(archivo_csv):
    lista_adyacencia = {}
    with open(archivo_csv, mode='r') as archivo:
        lector_csv = csv.reader(archivo)
        next(lector_csv)  # Ignorar el encabezado
        for fila in lector_csv:
            nodo = int(fila[0])  # Primer valor es el nodo
            conexiones = [int(x) for x in fila[1:] if x]  # Ignorar espacios vacíos
            lista_adyacencia[nodo] = conexiones
    return lista_adyacencia

# Función para leer la tabla de valores desde el archivo CSV generado
def leer_tabla_valores(archivo_csv):
    tabla_valores = []
    with open(archivo_csv, mode='r') as archivo:
        lector_csv = csv.reader(archivo)
        next(lector_csv)  # Ignorar el encabezado
        for fila in lector_csv:
            valores_fila = []
            for valor in fila[1:]:  # Ignorar el primer elemento, que es el nombre del nodo
                if valor.lower() == "inf":
                    valores_fila.append(float('inf'))
                else:
                    valores_fila.append(float(valor))
            tabla_valores.append(valores_fila)
    return tabla_valores

#Busqueda por escalada simple
def busqueda_escalada_simple(lista_adyacencia, tabla_valores, nodo_inicio, nodo_fin, sentido):
    nodo_actual = nodo_inicio
    ruta = [nodo_actual]
    revisados = set(ruta)
    
    while nodo_actual != nodo_fin:
        print(f"\nNodo actual: {nodo_actual}")
        sucesores = lista_adyacencia.get(nodo_actual, [])
        if not sucesores:
            print("Se alcanzo un maximo")
            return None

        #Ordenar sucesores en base al sentido de busqueda
        if sentido == "horario":
            sucesores.sort()
        elif sentido == "antihorario":
            sucesores.sort(reverse=True)
            
         # Filtrar sucesores para excluir los ya visitados
        sucesores_no_visitados = [nodo for nodo in sucesores if nodo not in revisados]
        if not sucesores_no_visitados:
            print("No hay sucesores sin visitar. No es posible continuar sin ciclo.")
            return None

        # Encontrar el sucesor no visitado con el valor más bajo en la tabla de valores
        mejor_sucesor = min(sucesores_no_visitados, key=lambda nodo: tabla_valores[nodo_actual-1][nodo-1])
        
        #Encontrar el sucesor con el valor mas bajo en la tabla de valores
        #mejor_sucesor = min( nodo_inicio,key = lambda nodo: tabla_valores[nodo_actual-1][nodo-1])
        #print(f"Nodos sucesores: {sucesores}")
        
        #agrega el mejor sucesor a la ruta y al conjunto de visitados
        ruta.append(mejor_sucesor)
        revisados.add(mejor_sucesor)
        nodo_actual = mejor_sucesor
    
    
    
    return ruta

def main():
    # Leer el archivo CSV para obtener la lista de adyacencia
    lista_adyacencia = leer_lista_adyacencia("lista_adyacencia.csv")
    
    # Leer la tabla de valores desde el archivo generado
    tabla_valores = leer_tabla_valores("matriz_relaciones.csv")

    # Preguntar por el nodo de inicio y el nodo final
    nodo_inicio = int(input("Ingresa el nodo de inicio: "))
    nodo_fin = int(input("Ingresa el nodo de destino: "))
    sentido = input("Ingresa el sentido de búsqueda (horario/antihorario): ").lower()

    # Ejecutar la búsqueda
    ruta = busqueda_escalada_simple(lista_adyacencia, tabla_valores, nodo_inicio, nodo_fin, sentido)
    
    if ruta:
        print(f"\nRuta encontrada: {ruta}")
    else:
        print("\nNo se encontró una ruta hacia el nodo destino.")

# Ejecutar el programa
if __name__ == "__main__":
    main()

