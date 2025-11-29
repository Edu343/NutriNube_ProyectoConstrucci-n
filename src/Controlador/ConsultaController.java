package Controlador;

import Modelo.Core.Controller;
import Modelo.Core.MainViewLayout;
import Modelo.POJOs.*;
import Vista.ConsultaFormularioView;

/**
 * Controlador para manejar la lógica de negocio asociada al formulario de una consulta nutricional.
 * Gestiona el cálculo de calorías, el guardado de datos y la navegación de retorno.
 */

public class ConsultaController extends Controller {

    public static final String MODO_AGREGAR = "AGREGAR";
    public static final String MODO_EDITAR = "EDITAR";

    public ConsultaController(String tag) {
        super(tag);
    }

    @Override
    public void handleCalcularCalorias(CaloriasCalculo calculoData, Paciente clavePaciente) {
    }

    @Override
    public void handleGuardarConsulta(Consulta consultaAGuardar) {
        
    }

    @Override
    public void handleSalir() {
        
    }

    @Override
    public void update() {
    }
}