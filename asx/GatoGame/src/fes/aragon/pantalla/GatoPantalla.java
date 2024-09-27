package fes.aragon.pantalla;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.lang.Math;

public class GatoPantalla implements ActionListener {
	private JButton[] buttons = new JButton[9];
	private JPanel panel;
	private JFrame frame;
	private boolean PlayerTurn, seBlock, seConk = true;
	private int victory, defeat, draw, nround = 0;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GatoPantalla window = new GatoPantalla();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GatoPantalla() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame("Juego de Gato / Tres en ralla");
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Se crea un JPanel con una cuadricula de 3x3 para tener el formato del tablero del juego del Gato
		panel = new JPanel();
		panel.setLayout(new GridLayout(3,3));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		//Se crea los botones y se le agrega la propiedad para que esperen alguna accion sobre ellos en este caso que sean pulsados por el jugador o NPC
		for (int i = 0; i < 9; i++) {
			buttons[i] = new JButton();
			buttons[i].setFont(new Font("Arial", Font.PLAIN,40));
			buttons[i].addActionListener(this);
			panel.add(buttons[i]);
		}

		frame.add(panel,BorderLayout.CENTER);
		frame.setSize(400,400);
		frame.setVisible(true);
		
		whoStart();
	}

	//Esta funcion es para poner que acciones los botones esperan que se ejecuten sobre ellos
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		JButton button = (JButton) e.getSource();
		//Esta es la cantidad de acciones que hace cuando el jugador lo presiona
		if(PlayerTurn) {
			button.setText("X");
			button.setEnabled(false);
			nround += 1;
			
		} else { //Esta parte hace la cantidad de acciones que el NPC puede realizar sobre el boton
			
			//Este if sirve para indicarle al NPC cuando es su turno para jugar
			//Primero es el caso cuando el NPC juega primero 
			if((nround == 0) || (nround == 2) || (nround == 4) || (nround == 6) || (nround == 8)) {
				
				//Inicia la serie de acciones que hace el NPC, solo puede hacer 1 por turno, para asegurar que sea asi se ponen en condicionales para que si una accion no se puede realizar, se realize la siguiente
				seBlock = blockPlayer();
				if (seBlock == false) {
					seBlock = true;
					seConk = conkO();
					if (seConk == false) {
						seConk = true;
						//En esta parte el NPC siempre tira en el centro cuando es su primer turno 
						if(buttons[4].getText().equals("")) {
							buttons[4].setText("O");
							buttons[4].setEnabled(false);
						}
						
					}
					
				}
				//Este es el caso donde el jugador va primero y el siguiente turno es del NPC
			} else if ((nround == 1) || (nround == 3) || (nround == 5) || (nround == 7)) {
				//Inicia la serie de acciones que hace el NPC, solo puede hacer 1 por turno, para asegurar que sea asi se ponen en condicionales para que si una accion no se puede realizar, se realize la siguiente
				seBlock = blockPlayer();
				if (seBlock == false) {
					seBlock = true;
					seConk = conkO();
					
					if (seConk == false) {
						seConk = true;
						//En esta parte como el jugador puede tirar en el centro al inicio se hace que el NPC escoja un boton a lo "aleatorio" y si ese boton que escogio ya ha sido seleccionado anterior mente entonces escoge otro
						int i = (int)(Math.random() * 8);
						if(buttons[i].getText().equals("")) {
							buttons[i].setText("O");
							buttons[i].setEnabled(false);
						} else {
							i = (int)(Math.random() * 8);
							buttons[i].setText("O");
							buttons[i].setEnabled(false);
						}
						
					}
				}
				
				
				
			}
			//Este es un contador que sirve para determinar en que ronda del juego se esta
			nround += 1;
		}
		
		//Este se considera una bandera que indica si le toca actuar al jugador o no
		PlayerTurn = !PlayerTurn;
		
		checkGanador();
		
	}
	
	//Esta funcion pregunta al usuario si quiere tener el primer turno o si mejor la maquina inicia el juego
	public void whoStart() {
		String[] opciones = {"Jugador","NPC"}; 
		var seleccion = JOptionPane.showOptionDialog(frame, "Quien empieza?", "Juguemos al Gato/Tres en raya", 0, 3, null, opciones, opciones[0]);
		if (seleccion == 0) {
			PlayerTurn = true;
		} else if (seleccion == 1) {
			PlayerTurn = false;
		}
	}
	
	//Esta funcion hace que la maquina detecte si el jugador este cerca de conseguir la victoria para bloquearlo, regresa un valor booleano para indicar si se tuno o no, que bloquear al jugador
	public boolean blockPlayer() {
		boolean selock = true;
		
			if ((buttons[1].getText().equals("X")) && (buttons[2].getText().equals("X")) || (buttons[4].getText().equals("X")) && (buttons[8].getText().equals("X")) || (buttons[3].getText().equals("X")) && (buttons[6].getText().equals("X"))) {
				if(buttons[0].getText().equals("")) {
					buttons[0].setText("O");
					buttons[0].setEnabled(false);
					return selock;
				} 
			}
			if ((buttons[0].getText().equals("X")) && (buttons[2].getText().equals("X")) || (buttons[4].getText().equals("X")) && (buttons[7].getText().equals("X"))) {
				if(buttons[1].getText().equals("")) {
					buttons[1].setText("O");
					buttons[1].setEnabled(false);	
					return selock;
				} 
			}
			if((buttons[0].getText().equals("X")) && (buttons[1].getText().equals("X")) || (buttons[4].getText().equals("X"))&&(buttons[6].getText().equals("X")) || (buttons[5].getText().equals("X"))&&(buttons[8].getText().equals("X")) ) {
				if(buttons[2].getText().equals("")) {
					buttons[2].setText("O");
					buttons[2].setEnabled(false);	
					return selock;
				} 
			} 
			if ((buttons[0].getText().equals("X")) && (buttons[6].getText().equals("X")) || (buttons[5].getText().equals("X")) && (buttons[4].getText().equals("X"))) {
				if(buttons[3].getText().equals("")) {
					buttons[3].setText("O");
					buttons[3].setEnabled(false);	
					return selock;
				} 
				
			}
			if ((buttons[0].getText().equals("X")) && (buttons[8].getText().equals("X")) || (buttons[2].getText().equals("X")) && (buttons[6].getText().equals("X")) || (buttons[1].getText().equals("X")) && (buttons[7].getText().equals("X")) || (buttons[3].getText().equals("X")) && (buttons[5].getText().equals("X"))) {
				if(buttons[4].getText().equals("")) {
					buttons[4].setText("O");
					buttons[4].setEnabled(false);	
					return selock;
				} 
			}
			if ((buttons[2].getText().equals("X")) && (buttons[8].getText().equals("X")) || (buttons[3].getText().equals("X")) && (buttons[4].getText().equals("X"))) {
				if(buttons[5].getText().equals("")) {
					buttons[5].setText("O");
					buttons[5].setEnabled(false);
					return selock;
				} 
			}
			if ((buttons[0].getText().equals("X")) && (buttons[3].getText().equals("X")) || (buttons[2].getText().equals("X")) && (buttons[4].getText().equals("X")) || (buttons[7].getText().equals("X")) && (buttons[8].getText().equals("X"))) {
				if(buttons[6].getText().equals("")) {
					buttons[6].setText("O");
					buttons[6].setEnabled(false);
					return selock;
				} 
			}
			if ((buttons[8].getText().equals("X")) && (buttons[6].getText().equals("X")) || (buttons[1].getText().equals("X")) && (buttons[4].getText().equals("X"))) {
				if(buttons[7].getText().equals("")) {
					buttons[7].setText("O");
					buttons[7].setEnabled(false);
					return selock;
				} 
			}
			if ((buttons[0].getText().equals("X")) && (buttons[4].getText().equals("X")) || (buttons[2].getText().equals("X")) && (buttons[5].getText().equals("X")) || (buttons[7].getText().equals("X")) && (buttons[6].getText().equals("X"))) {
				if(buttons[8].getText().equals("")) {
					buttons[8].setText("O");
					buttons[8].setEnabled(false);
					return selock;
				} 
			}
			return !selock;
	}

	//Esta funcion hace que la computadora detecte donde ha puesto una "O" y trata de poner otra junta para aumentar su chance de ganar y regresa un valor booleano para indicar si se pudo concatenar "O" o no
	public boolean conkO() {
		boolean seconk = true;
		for (int i = 0; i < 9 ; i += 1) {
			if(buttons[i].getText().equals("O")) {
				for (int j = i ; j < 9 ; j++) {
					if(buttons[j].getText().equals("")) {
						buttons[j].setText("O");
						buttons[j].setEnabled(false);
						return seconk;
					}
					
				}
				break;
			}
			
		}
		return seconk = false;
	}
	
	//Esta funcion checa cada que se pasa un turno si ya se cumplio alguna configuracion ganadora y quien gano
	public void checkGanador() {
		//Checa Filas
		for (int i = 0; i < 9; i += 3) {
            if (buttons[i].getText().equals(buttons[i+1].getText()) && buttons[i].getText().equals(buttons[i+2].getText()) && !buttons[i].isEnabled()) {
            	if (buttons[i].getText() == "X") {
            		JOptionPane.showMessageDialog(frame,  "El Jugador Gana!");
            		victory += 1;
            	} else if (buttons[i].getText() == "O") {
            		JOptionPane.showMessageDialog(frame,  "El NPC Gana!");
            		defeat += 1;
            	}
                resetGame();
                return;
            	}
		}	
	
		//Checa Columnas
		for (int i = 0; i < 3; i++) {
			if (buttons[i].getText().equals(buttons[i+3].getText()) && buttons[i].getText().equals(buttons[i+6].getText()) && !buttons[i].isEnabled()) {
				if (buttons[i].getText() == "X") {
	        		JOptionPane.showMessageDialog(frame,  "El Jugador Gana!");
	        		victory += 1;
	        	} else if (buttons[i].getText() == "O") {
	        		JOptionPane.showMessageDialog(frame,  "El NPC Gana!");
	        		defeat += 1;
	        	}
				resetGame();
				return;
			}
		}
		
		// Checa diagonales
        if (buttons[0].getText().equals(buttons[4].getText()) && buttons[0].getText().equals(buttons[8].getText()) && !buttons[0].isEnabled()) {
        	if (buttons[0].getText() == "X") {
        		JOptionPane.showMessageDialog(frame,  "El Jugador Gana!");
        		victory += 1;
        	} else if (buttons[0].getText() == "O") {
        		JOptionPane.showMessageDialog(frame,  "El NPC Gana!");
        		defeat += 1;
        	}
            resetGame();
            return;
        }
        if (buttons[2].getText().equals(buttons[4].getText()) && buttons[2].getText().equals(buttons[6].getText()) && !buttons[2].isEnabled()) {
        	if (buttons[2].getText() == "X") {
        		JOptionPane.showMessageDialog(frame,  "El Jugador Gana!");
        		victory += 1;
        	} else if (buttons[2].getText() == "O") {
        		JOptionPane.showMessageDialog(frame,  "El NPC Gana!");
        		defeat += 1;
        	}
            
            resetGame();
            return;
        }
        
        // Checa si hay empate
        boolean tie = true;
        for (int i = 0; i < 9; i++) {
            if (buttons[i].isEnabled()) {
                tie = false;
                break;
            }
        }
        if (tie) {
            JOptionPane.showMessageDialog(frame, "EMPATE!");
            draw += 1;
            resetGame();
        }
	}
	
	//Esta funcion resetea el juego, en especifico regresa los botones a su estado inicial y muestra las estadisticas de victorias, derrotas y empates
	public void resetGame() {
		JOptionPane.showMessageDialog(frame,"Victorias: " + victory + "\n" + "Derrotas: " + defeat + "\n" + "Empates: " + draw + "\n");
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
        nround = 0;
        whoStart();
    }
}
