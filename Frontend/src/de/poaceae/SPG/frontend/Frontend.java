// Version 0.1
// Rainer Winkler
// 08.02.2011 06:02

package de.poaceae.SPG.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import de.poaceae.SPG.basic.Main;
import de.poaceae.SPG.logic.DataManager;
import de.poaceae.SPG.logic.Logic;
import java.awt.GridBagLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;

import javax.swing.JToggleButton;

public class Frontend {

	private int myMousePosition = 0;

	// To communicate with other layers
	private Logic logic = null;  //  @jve:decl-index=0:
	private DataManager dataManager = null;  //  @jve:decl-index=0:

	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="-3,14"
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu File = null;
	private JMenu menuDisplay = null;
	private JMenuItem New = null;

	private ButtonGroup measureButtonGroup = new ButtonGroup();  //  @jve:decl-index=0:
	private ButtonGroup displayButtonGroup = new ButtonGroup();  //  @jve:decl-index=0:

	private JMenuItem ListByteCollector = null;
	private JMenuItem DisplayWavelength = null;
	private JMenuItem DisplayAllPixel = null;
	
	private FrontendHelper frontendHelper = null;  //  @jve:decl-index=0:


	private JPanel jPanelToDraw = null;

	private class MyDrawPanel extends JPanel {
		@Override public void paintComponent(Graphics g) {
			// Siehe Tutorial http://www.java-forum.org/awt-swing-swt/43939-zeichnen-swing-tutorial.html
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;
			logic.drawToMainPanel(g2d);

		}
	}	
	

	public int getMousePosition(){
		return myMousePosition;
	}

	private MyDrawPanel myDrawPanel;

	public void repaintMe(){
		myDrawPanel.repaint();
	}


	private JButton GetNewData = null;


	private JCheckBox jCheckBox = null;  //  @jve:decl-index=0:visual-constraint="43,257"


	private JPanel jInfoPanel = null;


	private JLabel MousePosition = null;

	private JTextField jTextFieldIntegrationTimeIn_ms = null;

	private JLabel jLabelIntegrationTimeIn_ms = null;

	private JPanel jSettingsPanel = null;

	private JPanel jTopPanel = null;

	private JPanel jButtonsMeasureTypePanel = null;

	private JPanel jSettingsSensitivyMeasurementsPanel = null;

	private JPanel jActionsPanel = null;

	private JRadioButton jDarkCurrentMeasureRadioButton = null;

	private JRadioButton jMeasure1RadioButton = null;

	private JTextField jSensitivityTimeTextField = null;

	private JPanel jSettingsSensitivitySubPanel = null;

	private JLabel jSensitivityTimeLabel = null;

	private JToggleButton jStartStopToggleButton = null;

	private JPanel jDisplayPanel = null;

	private JRadioButton jDisplayRawDataRadioButton = null;

	private JRadioButton jDisplayDarkCurrentRadioButton = null;

	private JRadioButton jDisplaySignal1RadioButton = null;

	private JRadioButton jRadioEqSignal1Button = null;

	private JLabel jLabelWellenlaenge = null;

	private JLabel jLabelWellenlaengeUnit = null;

	private JLabel jLabelPixelUnit = null;

	private JRadioButton jMeasure2RadioButton = null;

	private JRadioButton jDisplaySignal2RadioButton = null;

	private JRadioButton jRadioEqSignal2Button = null;

	private JRadioButton jDisplaySignal2_1RadioButton = null;

	private JRadioButton jRadioButtonDarkCurrent2 = null;

	private JRadioButton jRadioButtonMeasureDarkCurrent2 = null;

	private JButton jButtonWriteFile = null;

	private JButton jButtonStartThread = null;

	private JLabel jLabelCOMPort = null;

	private JTextField jTextFieldInputCOMPort = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JPanel jPanelMessage = null;

	private JLabel jLabelMessage = null;

	private MyDrawPanel getMyDrawPanel(){
		if (myDrawPanel == null){
			myDrawPanel = new MyDrawPanel();
			myDrawPanel.setLayout(new GridBagLayout());
			myDrawPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
				public void mouseMoved(java.awt.event.MouseEvent e) {
					// System.out.println("mouseMoved()"); // TODO Auto-generated Event stub mouseMoved()
					Point pos;
					Double lambda;
					pos = e.getPoint();

					//System.out.println( pos.x );
					//myMousePosition = pos.x + 500 ;
					myMousePosition = pos.x;
					
					//lambda = 387.66 + Math.pow((403.77/ ( (Integer) myMousePosition - 259.947)),(-6.75449));
					lambda = frontendHelper.diagramPosX2Lambda(myMousePosition);
					MousePosition.setText(((Integer) frontendHelper.Lambda2Pixel(lambda) ).toString());
					DecimalFormat df = new DecimalFormat( "0.00" );
					String s = df.format( lambda );
					jLabelWellenlaenge.setText( s );
				}
			});
		}
		return myDrawPanel;

	}

	//private Graphics2D g2jPaneltoDraw;


	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(1235, 905));
			jFrame.setTitle("SpektrographBeta");
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setContentPane(getJContentPane());
			jFrame.setVisible(true);
			jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					logic.closeApplication();
				}
			});
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setPreferredSize(new Dimension(500, 500));
			jContentPane.add(getMyDrawPanel(), BorderLayout.CENTER);
			//jContentPane.add(getJPanelToDraw(), BorderLayout.CENTER);
			jContentPane.add(getJTopPanel(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFile());
			jJMenuBar.add(getmenuDisplay());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes File	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFile() {
		if (File == null) {
			File = new JMenu();
			File.setText("Datei");
			File.add(getNew());
			File.add(getListByteCollector());
		}
		return File;
	}
	
	private JMenu getmenuDisplay() {
		if (menuDisplay == null) {
			menuDisplay = new JMenu();
			menuDisplay.setText("Display");
			menuDisplay.add(getDisplayWavelength());
			menuDisplay.add(getDisplayAllPixel());
		}
		return menuDisplay;
	}

	/**
	 * This method initializes New	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getNew() {
		if (New == null) {
			New = new JMenuItem();
			New.setText("Neu");
			New.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					logic.sendToSpectrograph();
				}
			});
		}
		return New;
	}

	public void startFrontend( Logic inLogic, DataManager inDataManager ){
		frontendHelper = new FrontendHelper();
		logic = inLogic;
		dataManager = inDataManager;
		getJFrame( );
		//	 myDrawPanel = (MyDrawPanel) jPanelToDraw;
		//	 myDrawPanel.repaint();
		//g2jPaneltoDraw = (Graphics2D) jPanelToDraw.getGraphics();
		//logic.setg2jPanelToDraw(g2jPaneltoDraw);
	}

	/**
	 * This method initializes ListByteCollector	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getListByteCollector() {
		if (ListByteCollector == null) {
			ListByteCollector = new JMenuItem();
			ListByteCollector.setText("ByteCollector ausgeben");
			ListByteCollector.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					logic.listByteCollector();
					myDrawPanel.repaint();
				}
			});
		}
		return ListByteCollector;
	}
	
	private JMenuItem getDisplayWavelength() {
		if (DisplayWavelength == null) {
			DisplayWavelength = new JMenuItem();
			DisplayWavelength.setText("Display by Wavelength");
			DisplayWavelength.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
                  frontendHelper.displayWaveLength();
				}
			});
		}
		return DisplayWavelength;
	}	
	
	private JMenuItem getDisplayAllPixel() {
		if (DisplayAllPixel == null) {
			DisplayAllPixel = new JMenuItem();
			DisplayAllPixel.setText("Display All Pixel of CCD");
			DisplayAllPixel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
                  frontendHelper.displayAllPixel();
				}
			});
		}
		return DisplayAllPixel;
	}		

	/**
	 * This method initializes jPanelToDraw	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelToDraw() {
		if (jPanelToDraw == null) {
			jPanelToDraw = new JPanel();
			jPanelToDraw.setLayout(new GridBagLayout());
		}
		return jPanelToDraw;
	}

	/**
	 * This method initializes GetNewData	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getGetNewData() {
		if (GetNewData == null) {
			GetNewData = new JButton();
			GetNewData.setText("Get New Data (Click me once)");
			GetNewData.setHorizontalAlignment(SwingConstants.LEFT);
			GetNewData.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					logic.sendToSpectrograph();
				}
			});
		}
		return GetNewData;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jInfoPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJInfoPanel() {
		if (jInfoPanel == null) {
			jLabelCOMPort = new JLabel();
			jLabelCOMPort.setText("COM Port Selected");
			jLabelPixelUnit = new JLabel();
			jLabelPixelUnit.setText("pixel");
			jLabelWellenlaengeUnit = new JLabel();
			jLabelWellenlaengeUnit.setText("nm");
			jLabelWellenlaenge = new JLabel();
			jLabelWellenlaenge.setText("       ");
			jLabelIntegrationTimeIn_ms = new JLabel();
			jLabelIntegrationTimeIn_ms.setText("Integration Time in ms");
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(5);
			MousePosition = new JLabel();
			MousePosition.setText("    ");
			jInfoPanel = new JPanel();
			jInfoPanel.setLayout(flowLayout);
			jInfoPanel.add(jLabelCOMPort, null);
			jInfoPanel.add(getJTextFieldInputCOMPort(), null);
			jInfoPanel.add(getJButtonStartThread(), null);
			jInfoPanel.add(getGetNewData(), null);
			jInfoPanel.add(MousePosition, null);
			jInfoPanel.add(jLabelPixelUnit, null);
			jInfoPanel.add(jLabelWellenlaenge, null);
			jInfoPanel.add(jLabelWellenlaengeUnit, null);
			jInfoPanel.add(jLabelIntegrationTimeIn_ms, null);
			jInfoPanel.add(getJTextFieldIntegrationTimeIn_ms(), null);
		}
		return jInfoPanel;
	}

	/**
	 * This method initializes jTextFieldIntegrationTimeIn_ms	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldIntegrationTimeIn_ms() {
		if (jTextFieldIntegrationTimeIn_ms == null) {
			jTextFieldIntegrationTimeIn_ms = new JTextField("0",6);
			jTextFieldIntegrationTimeIn_ms
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					logic.integrationTimeChanged(jTextFieldIntegrationTimeIn_ms.getText());
				}
			});
		}
		return jTextFieldIntegrationTimeIn_ms;
	}

	/**
	 * This method initializes jSettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJSettingsPanel() {
		if (jSettingsPanel == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.CENTER);
			jSettingsPanel = new JPanel();
			jSettingsPanel.setLayout(flowLayout1);
			jSettingsPanel.add(getJPanel(), null);
			jSettingsPanel.add(getJButtonsMeasureTypePanel(), null);
			jSettingsPanel.add(getJSettingsSensitivyMeasurementsPanel(), null);
			jSettingsPanel.add(getJActionsPanel(), null);
			jSettingsPanel.add(getJButtonWriteFile(), null);
			jSettingsPanel.add(getJDisplayPanel(), null);
		}
		return jSettingsPanel;
	}

	/**
	 * This method initializes jTopPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJTopPanel() {
		if (jTopPanel == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.ipadx = 157;
			gridBagConstraints6.gridy = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.ipadx = 502;
			gridBagConstraints5.ipady = 0;
			gridBagConstraints5.gridheight = 1;
			gridBagConstraints5.gridy = 0;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.ipadx = 502;
			gridBagConstraints7.ipady = 0;
			gridBagConstraints7.gridheight = 1;
			gridBagConstraints7.gridy = 2;			
			jTopPanel = new JPanel();
			jTopPanel.setLayout(new GridBagLayout());
			jTopPanel.add(getJInfoPanel(), gridBagConstraints5);
			jTopPanel.add(getJSettingsPanel(), gridBagConstraints6);
			jTopPanel.add(getJPanelMessage2(), gridBagConstraints7);
		}
		return jTopPanel;
	}

	/**
	 * This method initializes jButtonsMeasureTypePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJButtonsMeasureTypePanel() {
		if (jButtonsMeasureTypePanel == null) {
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 1;
			gridBagConstraints13.gridy = 1;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 1;
			gridBagConstraints12.gridy = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridy = 2;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridy = 1;
			jButtonsMeasureTypePanel = new JPanel();
			jButtonsMeasureTypePanel.setLayout(new GridBagLayout());
			jButtonsMeasureTypePanel.add(getJDarkCurrentMeasureRadioButton(), gridBagConstraints);
			jButtonsMeasureTypePanel.add(getJMeasure1RadioButton(), gridBagConstraints1);
			jButtonsMeasureTypePanel.add(getJMeasure2RadioButton(), gridBagConstraints12);
			jButtonsMeasureTypePanel.add(getJRadioButtonMeasureDarkCurrent2(), gridBagConstraints13);
		}
		return jButtonsMeasureTypePanel;
	}

	/**
	 * This method initializes jSettingsSensitivyMeasurementsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJSettingsSensitivyMeasurementsPanel() {
		if (jSettingsSensitivyMeasurementsPanel == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.anchor = GridBagConstraints.NORTH;
			jSettingsSensitivyMeasurementsPanel = new JPanel();
			jSettingsSensitivyMeasurementsPanel.setLayout(new GridBagLayout());
			jSettingsSensitivyMeasurementsPanel.add(getJSettingsSensitivitySubPanel(), gridBagConstraints3);
		}
		return jSettingsSensitivyMeasurementsPanel;
	}

	/**
	 * This method initializes jActionsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJActionsPanel() {
		if (jActionsPanel == null) {
			jActionsPanel = new JPanel();
			jActionsPanel.setLayout(new GridBagLayout());
			jActionsPanel.add(getJStartStopToggleButton(), new GridBagConstraints());
		}
		return jActionsPanel;
	}

	/**
	 * This method initializes jDarkCurrentMeasureRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJDarkCurrentMeasureRadioButton() {
		if (jDarkCurrentMeasureRadioButton == null) {
			// This is the default button
			// In DataManager.java therefore 
			// private int measureSet = setMeasureDark; is written for proper initialization
			jDarkCurrentMeasureRadioButton = new JRadioButton("Measure Dark Current",true);
			jDarkCurrentMeasureRadioButton
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataManager.setMeasure(dataManager.setMeasureDark); 
				}
			});
			measureButtonGroup.add(jDarkCurrentMeasureRadioButton);
		}
		return jDarkCurrentMeasureRadioButton;
	}

	/**
	 * This method initializes jMeasure1RadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJMeasure1RadioButton() {
		if (jMeasure1RadioButton == null) {
			jMeasure1RadioButton = new JRadioButton();
			jMeasure1RadioButton.setText("Measure Signal 1");
			jMeasure1RadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataManager.setMeasure(dataManager.setMeasureSignal1);
				}
			});
			measureButtonGroup.add(jMeasure1RadioButton);
		}
		return jMeasure1RadioButton;
	}

	/**
	 * This method initializes jSensitivityTimeTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJSensitivityTimeTextField() {
		if (jSensitivityTimeTextField == null) {
			jSensitivityTimeTextField = new JTextField("0",6);
		}
		return jSensitivityTimeTextField;
	}

	/**
	 * This method initializes jSettingsSensitivitySubPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJSettingsSensitivitySubPanel() {
		if (jSettingsSensitivitySubPanel == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.anchor = GridBagConstraints.NORTH;
			jSensitivityTimeLabel = new JLabel();
			jSensitivityTimeLabel.setText("Integration Time for Sensitivity");
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.gridx = -1;
			gridBagConstraints2.gridy = -1;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.gridwidth = 6;
			jSettingsSensitivitySubPanel = new JPanel();
			jSettingsSensitivitySubPanel.setLayout(new GridBagLayout());
			jSettingsSensitivitySubPanel.add(jSensitivityTimeLabel, gridBagConstraints4);
			jSettingsSensitivitySubPanel.add(getJSensitivityTimeTextField(), gridBagConstraints2);
		}
		return jSettingsSensitivitySubPanel;
	}

	/**
	 * This method initializes jStartStopToggleButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJStartStopToggleButton() {
		if (jStartStopToggleButton == null) {
			jStartStopToggleButton = new JToggleButton();
			jStartStopToggleButton.setText("Start Measurement");
			jStartStopToggleButton.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if ( e.getStateChange() == e.SELECTED ){
						dataManager.startMeasurement();
					}
					else
					{
						dataManager.stopMeasurement();
					} 
				}
			});
		}
		return jStartStopToggleButton;
	}

	/**
	 * This method initializes jDisplayPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJDisplayPanel() {
		if (jDisplayPanel == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(5);
			gridLayout1.setColumns(2);
			jDisplayPanel = new JPanel();
			jDisplayPanel.setLayout(gridLayout1);
			jDisplayPanel.add(getJDisplayDarkCurrentRadioButton(), null);
			jDisplayPanel.add(getJRadioButtonDarkCurrent2(), null);
			jDisplayPanel.add(getJDisplayRawDataRadioButton(), null);
			jDisplayPanel.add(getJDisplaySignal1RadioButton(), null);
			jDisplayPanel.add(getJRadioEqSignal1Button(), null);
			jDisplayPanel.add(getJDisplaySignal2RadioButton(), null);
			jDisplayPanel.add(getJRadioEqSignal2Button(), null);
			jDisplayPanel.add(getJDisplaySignal2_1RadioButton(), null);
		}
		return jDisplayPanel;
	}

	/**
	 * This method initializes jDisplayRawDataRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJDisplayRawDataRadioButton() {
		if (jDisplayRawDataRadioButton == null) {
			jDisplayRawDataRadioButton = new JRadioButton("Display Raw Data",true);
			jDisplayRawDataRadioButton
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataManager.setDisplay(dataManager.setDisplayRaw);
				}
			});
			displayButtonGroup.add(jDisplayRawDataRadioButton);
		}
		return jDisplayRawDataRadioButton;
	}

	/**
	 * This method initializes jDisplayDarkCurrentRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJDisplayDarkCurrentRadioButton() {
		if (jDisplayDarkCurrentRadioButton == null) {
			jDisplayDarkCurrentRadioButton = new JRadioButton("Display Dark Current",false);
			jDisplayDarkCurrentRadioButton
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataManager.setDisplay(dataManager.setDisplayDark); 
				}
			});
			displayButtonGroup.add(jDisplayDarkCurrentRadioButton);
		}
		return jDisplayDarkCurrentRadioButton;
	}

	/**
	 * This method initializes jDisplaySignal1RadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJDisplaySignal1RadioButton() {
		if (jDisplaySignal1RadioButton == null) {
			jDisplaySignal1RadioButton = new JRadioButton("Display Signal 1",false);
			jDisplaySignal1RadioButton
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataManager.setDisplay(dataManager.setDisplaySignal1); 
				}
			});
			displayButtonGroup.add(jDisplaySignal1RadioButton);
		}
		return jDisplaySignal1RadioButton;
	}

	/**
	 * This method initializes jRadioEqSignal1Button	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioEqSignal1Button() {
		if (jRadioEqSignal1Button == null) {
			jRadioEqSignal1Button = new JRadioButton("Display Processed Signal 1");
			jRadioEqSignal1Button.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataManager.setDisplay(dataManager.setDisplayEqSignal1);
				}
			});
		}
		displayButtonGroup.add(jRadioEqSignal1Button);
		return jRadioEqSignal1Button;
	}

	/**
	 * This method initializes jMeasure2RadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJMeasure2RadioButton() {
		if (jMeasure2RadioButton == null) {
			jMeasure2RadioButton = new JRadioButton();
			jMeasure2RadioButton.setText("Measure Signal 2");
			jMeasure2RadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataManager.setMeasure(dataManager.setMeasureSignal2);
				}
			});
		}
		measureButtonGroup.add(jMeasure2RadioButton);
		return jMeasure2RadioButton;
	}

	/**
	 * This method initializes jDisplaySignal2RadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJDisplaySignal2RadioButton() {
		if (jDisplaySignal2RadioButton == null) {
			jDisplaySignal2RadioButton = new JRadioButton();
			jDisplaySignal2RadioButton.setText("Display Signal 2");
			jDisplaySignal2RadioButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							dataManager.setDisplay(dataManager.setDisplaySignal2);
						}
					});
		}
		displayButtonGroup.add(jDisplaySignal2RadioButton);
		return jDisplaySignal2RadioButton;
	}

	/**
	 * This method initializes jRadioEqSignal2Button	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioEqSignal2Button() {
		if (jRadioEqSignal2Button == null) {
			jRadioEqSignal2Button = new JRadioButton();
			jRadioEqSignal2Button.setText("Display Processed Signal 2");
			jRadioEqSignal2Button.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataManager.setDisplay(dataManager.setDisplayEqSignal2);
				}
			});
		}
		displayButtonGroup.add(jRadioEqSignal2Button);
		return jRadioEqSignal2Button;
	}

	/**
	 * This method initializes jDisplaySignal2_1RadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJDisplaySignal2_1RadioButton() {
		if (jDisplaySignal2_1RadioButton == null) {
			jDisplaySignal2_1RadioButton = new JRadioButton();
			jDisplaySignal2_1RadioButton.setText("Display Ratio Signal 2 to 1");
			jDisplaySignal2_1RadioButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							dataManager.setDisplay(dataManager.setDisplayEqSignal2div1);
						}
					});
		}
		displayButtonGroup.add(jDisplaySignal2_1RadioButton);
		return jDisplaySignal2_1RadioButton;
	}

public FrontendHelper returnFrontendHelper(){
	return frontendHelper;
}

/**
 * This method initializes jRadioButtonDarkCurrent2	
 * 	
 * @return javax.swing.JRadioButton	
 */
private JRadioButton getJRadioButtonDarkCurrent2() {
	if (jRadioButtonDarkCurrent2 == null) {
		jRadioButtonDarkCurrent2 = new JRadioButton();
		jRadioButtonDarkCurrent2.setText("Display Dark Current 2");
		jRadioButtonDarkCurrent2
		.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dataManager.setDisplay(dataManager.setDisplayDark2); 
			}
		});
		displayButtonGroup.add(jRadioButtonDarkCurrent2);
	}
	return jRadioButtonDarkCurrent2;
}

/**
 * This method initializes jRadioButtonMeasureDarkCurrent2	
 * 	
 * @return javax.swing.JRadioButton	
 */
private JRadioButton getJRadioButtonMeasureDarkCurrent2() {
	if (jRadioButtonMeasureDarkCurrent2 == null) {
		jRadioButtonMeasureDarkCurrent2 = new JRadioButton();
		jRadioButtonMeasureDarkCurrent2.setText("Measure Dark Current 2");
		jRadioButtonMeasureDarkCurrent2
		.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dataManager.setMeasure(dataManager.setMeasureDark2); 
			}
		});
		measureButtonGroup.add(jRadioButtonMeasureDarkCurrent2);
	}
	return jRadioButtonMeasureDarkCurrent2;
}

/**
 * This method initializes jButtonWriteFile	
 * 	
 * @return javax.swing.JButton	
 */
private JButton getJButtonWriteFile() {
	if (jButtonWriteFile == null) {
		jButtonWriteFile = new JButton();
		jButtonWriteFile.setText("Write File");
		jButtonWriteFile
				.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
					public void propertyChange(java.beans.PropertyChangeEvent e) {
						if ((e.getPropertyName().equals("enabled"))) {
							logic.activateFile();
							System.out.println("propertyChange(enabled)"); // TODO Auto-generated property Event stub "enabled" 
						}
					}
				});
		jButtonWriteFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logic.activateFile();
				System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
			}
		});
	}
	return jButtonWriteFile;
}

/**
 * This method initializes jButtonStartThread	
 * 	
 * @return javax.swing.JButton	
 */
private JButton getJButtonStartThread() {
	if (jButtonStartThread == null) {
		jButtonStartThread = new JButton();
		jButtonStartThread.setText("Start Thread to communicate to device");
		jButtonStartThread.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Main.start_thread_to_communicate(logic, jTextFieldInputCOMPort.getText());
			}
		});
	}
	return jButtonStartThread;
}

/**
 * This method initializes jTextFieldInputCOMPort	
 * 	
 * @return javax.swing.JTextField	
 */
private JTextField getJTextFieldInputCOMPort() {
	if (jTextFieldInputCOMPort == null) {
		jTextFieldInputCOMPort = new JTextField("COM1",6);
		
	}
	return jTextFieldInputCOMPort;
}

/**
 * This method initializes jPanelMessage	
 * 	
 * @return javax.swing.JPanel	
 */
private JPanel getJPanelMessage() {
	if (jPanelMessage == null) {
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.gridx = -1;
		gridBagConstraints7.gridy = -1;
	}
	return jPanelMessage;
}

/**
 * This method initializes jPanel	
 * 	
 * @return javax.swing.JPanel	
 */
private JPanel getJPanel() {
	if (jPanel == null) {
		jPanel = new JPanel();
		jPanel.setLayout(new GridBagLayout());
	}
	return jPanel;
}

/**
 * This method initializes jPanel1	
 * 	
 * @return javax.swing.JPanel	
 */
private JPanel getJPanel1() {
	if (jPanel1 == null) {
		jPanel1 = new JPanel();
		jPanel1.setLayout(new GridBagLayout());
	}
	return jPanel1;
}

/**
 * This method initializes jPanelMessage	
 * 	
 * @return javax.swing.JPanel	
 */
private JPanel getJPanelMessage2() {
	if (jPanelMessage == null) {
		jLabelMessage = new JLabel();
		jLabelMessage.setText("");
		jPanelMessage = new JPanel();
		jPanelMessage.setLayout(new FlowLayout());
		jPanelMessage.add(jLabelMessage, null);
	}
	return jPanelMessage;
}

public void setMessage(String message){
	jLabelMessage.setText(message);
}

}