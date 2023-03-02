package com.view;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.assessment.SearchTabController;

public class SearchTabView {

	private JFrame frame;
	private JPanel panel;
	private JTextField directoryPathTextField;
	private JTextField searchStringTextField;
	private JTextArea resultsTextArea;

	public SearchTabView() {
		frame = new JFrame("Search Tab");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);

		panel = new JPanel();
		panel.setLayout(null);

		JLabel directoryPathLabel = new JLabel("Directory Path:");
		directoryPathLabel.setBounds(20, 20, 100, 20);
		panel.add(directoryPathLabel);

		directoryPathTextField = new JTextField();
		directoryPathTextField.setBounds(130, 20, 400, 20);
		panel.add(directoryPathTextField);

		JLabel searchStringLabel = new JLabel("Search String:");
		searchStringLabel.setBounds(20, 50, 100, 20);
		panel.add(searchStringLabel);

		searchStringTextField = new JTextField();
		searchStringTextField.setBounds(130, 50, 400, 20);
		panel.add(searchStringTextField);

		JButton searchButton = new JButton("Search");
		searchButton.setBounds(20, 80, 100, 20);
		panel.add(searchButton);

		resultsTextArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(resultsTextArea);
		scrollPane.setBounds(20, 110, 1000, 500);
		panel.add(scrollPane);

		frame.add(panel);
		frame.setVisible(true);

		searchButton.addActionListener(e -> {
			String directoryPath = directoryPathTextField.getText();
			String searchString = searchStringTextField.getText();
			SearchTabController controller = new SearchTabController();
			String results;
			try {
				results = controller.searchDirectory(directoryPath, searchString);
				resultsTextArea.setText(results);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			resultsTextArea.setText(results);
		});
	}

	public static void main(String[] args) {
		new SearchTabView();
	}
}