/* $Id$ */
/***************************************************************************
 *                      (C) Copyright 2003 - Marauroa                      *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.client.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;
import java.util.StringTokenizer;

import games.stendhal.client.*;
import marauroa.client.*;

/**
 * Summary description for LoginDialog
 * 
 */
public class LoginDialog extends JDialog {
	private static final long serialVersionUID = 4436228792112530975L;

	// Variables declaration
	private JLabel usernameLabel;

	private JLabel serverLabel;

	private JLabel serverPortLabel;

	private JLabel passwordLabel;

	private JCheckBox saveLoginBox;

	private JTextField usernameField;

	private JPasswordField passwordField;

	private JComboBox serverField;

	private JTextField serverPortField;

	private JButton loginButton;

	private JPanel contentPane;

	// End of variables declaration
	private StendhalClient client;

	private Frame owner;

	public LoginDialog(Frame owner, StendhalClient client) {
		super(owner, true);
		this.client = client;
		this.owner = owner;
		initializeComponent();

		this.setVisible(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Windows Form Designer. Otherwise, retrieving design
	 * might not work properly. Tip: If you must revise this method, please
	 * backup this GUI file for JFrameBuilder to retrieve your design properly
	 * in future, before revising this method.
	 */
	private void initializeComponent() {
		serverLabel = new JLabel("Choose your Stendhal Server");
		serverPortLabel = new JLabel("Choose your Server-port");
		usernameLabel = new JLabel("Type your username");
		passwordLabel = new JLabel("Type your password");
		saveLoginBox = new JCheckBox("Remember login info");
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		serverField = new JComboBox();
		serverField.setEditable(true);
		serverPortField = new JTextField("32160");
		loginButton = new JButton();
		contentPane = (JPanel) this.getContentPane();

		StringTokenizer loginInfo = new StringTokenizer(getLoginInfo());

		//
		// serverField
		//
		for (String server : stendhal.SERVERS_LIST) {
			serverField.addItem(server);
		}
		// Added support for saving port number intensifly@gmx.com

		int tokens = loginInfo.countTokens();
		if (tokens >= 3) {
			saveLoginBox.setSelected(true);
			//
			// serverField
			//
			serverField.setSelectedItem(loginInfo.nextToken());
			//
			// usernameField
			//
			usernameField.setText(loginInfo.nextToken());
			//
			// passwordField
			//
			passwordField.setText(loginInfo.nextToken());
		}
		// Added support for saving port number intensifly@gmx.com
		if (tokens == 4) {
			//
			// serverPortField
			//
			serverPortField.setText(loginInfo.nextToken());
		}
		// loginButton
		loginButton.setText("Login to Server");
		loginButton.setMnemonic(KeyEvent.VK_L);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginButton_actionPerformed(e, saveLoginBox.isSelected());
			}
		});
		//
		// contentPane
		//
		contentPane.setLayout(new GridBagLayout());
		contentPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEtchedBorder(), BorderFactory.createEmptyBorder(5, 5, 5,
				5)));
		GridBagConstraints c = new GridBagConstraints();

		// row 0
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(4, 4, 15, 4);
		c.gridx = 0;// column
		c.gridy = 0;// row
		contentPane.add(serverLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		contentPane.add(serverField, c);
		// row 1
		c.insets = new Insets(4, 4, 4, 4);
		c.gridx = 0;
		c.gridy = 1;
		contentPane.add(serverPortLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(4, 4, 4, 4);
		c.fill = GridBagConstraints.BOTH;
		contentPane.add(serverPortField, c);
		// row 2
		c.insets = new Insets(4, 4, 4, 4);
		c.gridx = 0;
		c.gridy = 2;
		contentPane.add(usernameLabel, c);
		c.gridx = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		contentPane.add(usernameField, c);
		// row 3
		c.gridx = 0;
		c.gridy = 3;
		c.fill = GridBagConstraints.NONE;
		contentPane.add(passwordLabel, c);
		c.gridx = 1;
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		contentPane.add(passwordField, c);
		// row 4
		c.gridx = 0;
		c.gridy = 4;
		c.fill = GridBagConstraints.NONE;
		contentPane.add(saveLoginBox, c);
		c.gridx = 1;
		c.gridy = 4;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(15, 4, 4, 4);
		contentPane.add(loginButton, c);

		//
		// LoginDialog
		//
		this.setTitle("Login to Server");
		this.setResizable(false);
		this.setSize(new Dimension(410, 205));
		this.setLocationRelativeTo(owner);
	}

	// TODO: Never used? remove me
	// /** Add Component Without a Layout Manager (Absolute Positioning) */
	// private void addComponent(Container container, Component c, int x, int y,
	// int width, int height) {
	// c.setBounds(x, y, width, height);
	// container.add(c);
	// }

	private void loginButton_actionPerformed(ActionEvent e,
			boolean saveLoginBoxStatus) {
		final String username = usernameField.getText().trim();
		final String password = new String(passwordField.getPassword());
		final String server = ((String) serverField.getSelectedItem()).trim();
		int port = 32160;
		final int finalPort;// port couldnt be accessed from inner class
		final ProgressBar progressBar = new ProgressBar(owner);

		try {
			port = Integer.parseInt(serverPortField.getText().trim());
			// Support for saving port number. Only save when input is a number
			// intensifly@gmx.com

			if (saveLoginBoxStatus)
				saveLoginInfo(server, username, password, serverPortField
						.getText());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(owner,
					"You typed in a invalid port, try again", "Invalid port",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		finalPort = port;

		/* seprate thread for connection proccess added by TheGeneral */
		// run the connection procces in separate thread
		Thread m_connectionThread = new Thread() {
			public void run() {
				progressBar.start();// intialize progress bar
				setVisible(false);// hide this screen when attempting to
									// connect

				try {
					client.connect(server, finalPort);
					progressBar.step();// for each major connection milestone
										// call step()
				} catch (Exception ex) {
					progressBar.cancel();// if something goes horribly just
											// cancel the progressbar
					setVisible(true);
					JOptionPane
							.showMessageDialog(owner,
									"Stendhal can't find a Internet connection for getting online");

					ex.printStackTrace();

					return;
				}

				try {
					if (client.login(username, password) == false) {
						String result = client.getEvent();
						if (result == null) {
							result = "Server is not available right now. Check it is online";
						}
						progressBar.cancel();
						setVisible(true);
						JOptionPane.showMessageDialog(owner, result,
								"Login status", JOptionPane.ERROR_MESSAGE);
					} else {
						progressBar.step();
						progressBar.finish();
						// try {//wait just long enough for progress bar to
						// close
						// progressBar.m_run.join();
						// }catch (InterruptedException ie) {}

						setVisible(false);
						owner.setVisible(false);
						stendhal.doLogin = true;
					}
				} catch (ariannexpTimeoutException ex) {
					progressBar.cancel();
					setVisible(true);
					JOptionPane.showMessageDialog(owner,
							"Can't connect to server. Server down?",
							"Login status", JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex) {
					progressBar.cancel();
					setVisible(true);
					JOptionPane.showMessageDialog(owner,
							"Connection error. Online?", "Login status",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		m_connectionThread.start();
	}

	/*
	 * Author: Da_MusH Description: Methods for saving and loading login
	 * information to disk. These should probably make a separate class in the
	 * future, but it will work for now. comment: Thegeneral has added encoding
	 * for password and username
	 */

	private void saveLoginInfo(String server, String usrName, String pwd,
			String port) {
		Encoder encode = new Encoder();
		try {
			File loginFile = new File(stendhal.STENDHAL_FOLDER + "user.dat");
			PrintWriter fos = new PrintWriter(loginFile);

			fos.print(encode.encode(server + " " + usrName + " " + pwd + " "
					+ port));
			fos.close();
		} catch (IOException ioex) {
			JOptionPane
					.showMessageDialog(
							this,
							"Something went wrong when saving login information, nothing saved",
							"Login information save problem",
							JOptionPane.WARNING_MESSAGE);
		}
	}

	private String getLoginInfo() {
		Encoder decode = new Encoder();
		String loginLine = "";

		try {
			FileReader fr = new FileReader(stendhal.STENDHAL_FOLDER
					+ "user.dat");
			BufferedReader fin = new BufferedReader(fr);

			loginLine = decode.decode(fin.readLine());
			if (loginLine == null)
				loginLine = "no_data";
			fin.close();
		} catch (FileNotFoundException fnfe) {
			loginLine = "no_file";
		} catch (IOException ioex) {
			JOptionPane
					.showMessageDialog(
							this,
							"Something went wrong when loading login information, nothing loaded",
							"Login information load problem",
							JOptionPane.WARNING_MESSAGE);
		}

		return loginLine;
	}

	public static void main(String args[]) {
		new LoginDialog(null, null).setVisible(true);
	}
}
