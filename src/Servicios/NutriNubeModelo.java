package Servicios;

import java.sql.SQLException;


import java.util.HashSet;
import java.util.Set;
import Core.Observer;
import DAO.*;
import POJOs.*;

public class NutriNubeModelo {
    private Set<Observer> observers;
    private NutriologoDAO nutriologoDAO;
    private PacienteDAO pacienteDAO;
    private ConsultaDAO consultaDAO;    
    private HashingServicio securityServicio;
    private NutricionServicio nutricionServicio;
    private Validaciones validacionServicio;
    private MacronutrientesServicio macronutrientesServicio;
    
    private Nutriologo nutriologoActual;
    private Paciente pacienteSeleccionado;
    private Consulta consultaSeleccionada;

    public NutriNubeModelo() {
        this.observers = new HashSet<>();
        this.nutriologoDAO = new NutriologoDAO();
        this.pacienteDAO = new PacienteDAO();
        this.consultaDAO = new ConsultaDAO();
        this.securityServicio = new HashingServicio();
        this.nutricionServicio = new NutricionServicio();
        this.validacionServicio = new Validaciones();
        this.macronutrientesServicio = new MacronutrientesServicio();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String evento) {
        for (Observer observer : observers) {
            observer.update();
        }
    }
    
    public boolean login(String usuario, String contrasena) {
        Nutriologo nutriologo = null;
		try {
			nutriologo = nutriologoDAO.read(usuario);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (nutriologo != null && securityServicio.checkPassword(contrasena, nutriologo.getContrasena())) {
            this.nutriologoActual = nutriologo;
            notifyObservers("LOGIN_SUCCESS");
            return true;
        }
        notifyObservers("LOGIN_FAILED");
        return false;
    }
    
    public void logout() {
        this.nutriologoActual = null;
        this.pacienteSeleccionado = null;
        this.consultaSeleccionada = null;
        notifyObservers("LOGOUT");
    }
    
    public void agregarPaciente(Paciente paciente) {
        if (validacionServicio.isValidPaciente(paciente)) {
            paciente.setClave_nutriologo(nutriologoActual.getClave_nutriologo());
            try {
				pacienteDAO.create(paciente);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            notifyObservers("PACIENTE_AGREGADO");
           
        }
        notifyObservers("PACIENTE_INVALIDO");
       
    }
    
    public void eliminarPaciente(String clavePaciente) {
        pacienteDAO.delete(clavePaciente);
        notifyObservers("PACIENTE_ELIMINADO");
    }
    
    public void seleccionarPaciente(String clavePaciente) {
        try {
			this.pacienteSeleccionado = pacienteDAO.read(clavePaciente);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        notifyObservers("PACIENTE_SELECCIONADO");
    }
    
    public void guardarConsulta(Consulta consulta) {
        if (validacionServicio.isValidConsulta(consulta)) {
            ConsultaDAO.create(consulta);
            notifyObservers("CONSULTA_GUARDADA");
        }
        notifyObservers("CONSULTA_INVALIDA");
    }
    

    
    public void seleccionarConsulta(String claveConsulta) {
        try {
			this.consultaSeleccionada = consultaDAO.read(claveConsulta);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        notifyObservers("CONSULTA_SELECCIONADA");
    }

    public Set<Observer> getObservers() {
        return observers;
    }

    public NutriologoDAO getNutriologoDAO() {
        return nutriologoDAO;
    }

    public PacienteDAO getPacienteDAO() {
        return pacienteDAO;
    }

    public ConsultaDAO getConsultaDAO() {
        return consultaDAO;
    }

    public HashingServicio getSecurityServicio() {
        return securityServicio;
    }

    public NutricionServicio getNutricionServicio() {
        return nutricionServicio;
    }

    public Validaciones getValidacionServicio() {
        return validacionServicio;
    }

    public MacronutrientesServicio getMacronutrientesServicio() {
        return macronutrientesServicio;
    }
    
    public Nutriologo getNutriologoActual() {
        return nutriologoActual;
    }
    
    public Paciente getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }
    
    public Consulta getConsultaSeleccionada() {
        return consultaSeleccionada;
    }

    public void setObservers(Set<Observer> observers) {
        this.observers = observers;
    }

    public void setNutriologoDAO(NutriologoDAO nutriologoDAO) {
        this.nutriologoDAO = nutriologoDAO;
    }

    public void setPacienteDAO(PacienteDAO pacienteDAO) {
        this.pacienteDAO = pacienteDAO;
    }

    public void setConsultaDAO(ConsultaDAO consultaDAO) {
        this.consultaDAO = consultaDAO;
    }

    public void setSecurityServicio(HashingServicio securityServicio) {
        this.securityServicio = securityServicio;
    }

    public void setNutricionServicio(NutricionServicio nutricionServicio) {
        this.nutricionServicio = nutricionServicio;
    }

    public void setValidacionServicio(Validaciones validacionServicio) {
        this.validacionServicio = validacionServicio;
    }

    public void setMacronutrientesServicio(MacronutrientesServicio macronutrientesServicio) {
        this.macronutrientesServicio = macronutrientesServicio;
    }

	public void eliminarConsulta(String claveConsulta) {
		// TODO Auto-generated method stub
		
	}
}