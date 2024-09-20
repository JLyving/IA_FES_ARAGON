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

public class GatoPantalla implements ActionListener {
	private JButton[] buttons = new JButton[9];
	private JPanel panel;
	private JFrame frame;
	private boolean PlayerTurn = true;
	private int victory, defeat, draw = 0;
	
	

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
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(3,3));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		for (int i = 0; i < 9; i++) {
			buttons[i] = new JButton();
			buttons[i].setFont(new Font("Arial", Font.PLAIN,40));
			buttons[i].addActionListener(this);
			panel.add(buttons[i]);
		}

		frame.add(panel,BorderLayout.CENTER);
		frame.setSize(400,400);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton button = (JButton) e.getSource();
		if(PlayerTurn) {
			button.setText("X");
		} else {
			button.setText("O");
		}
		button.setEnabled(false);
		PlayerTurn = !PlayerTurn;
		
		checkGanador();
		
	}
	
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
	
	public void resetGame() {
		JOptionPane.showMessageDialog(frame,"Victorias: " + victory + "\n" + "Derrotas: " + defeat + "\n" + "Empates: " + draw + "\n");
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
        PlayerTurn = true;
    }
}
