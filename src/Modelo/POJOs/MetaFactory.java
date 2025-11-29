package POJOs;



public class MetaFactory {

    
    public static MetaNutricional obtenerMeta(int opcion) {
        switch (opcion) {
            case 1: // Disminuir grasa
                return new MetaNutricional(0.48, 0.24, 0.28);

            case 2: // Aumentar masa muscular
                return new MetaNutricional(0.50, 0.23, 0.27);

            case 3: // Recomposición corporal
                return new MetaNutricional(0.48, 0.24, 0.28);

            case 4: // Salud / neutro
                return new MetaNutricional(0.52, 0.22, 0.26);

            default:
                throw new IllegalArgumentException("Meta no válida");
        }
    }
}

