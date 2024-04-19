package oopMTBS;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class CustomerFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String PROPERTIES_FILE = "movieData.properties";
	private JPanel contentPane;
	private JTable tableAvMovies;
	private DefaultTableModel tableModel;
	private OpeningFrame openingFrame;
	private Properties movieProperties;
	private JLabel lblTITLE;
    private JLabel lblSHOWDATE;
    private JLabel lblCINEMA;
    private JLabel lblPRICE;
    private JLabel lblTOTAL;
    private JSpinner spinnerTicketQuantity;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerFrame frame = new CustomerFrame();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CustomerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 869, 537);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionText);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		panel.setBackground(new Color(178, 34, 34));
		panel.setBounds(10, 100, 550, 380);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 550, 380);
		panel.add(scrollPane);
		
		movieProperties = loadMovieData(); 
		
		tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Cinema","Movie Title", "Genre", "Duration", "Showtime", "Price", "Seats Available"});
        tableAvMovies = new JTable(tableModel);
        tableAvMovies.setForeground(new Color(255, 255, 255));

		tableAvMovies.getColumnModel().getColumn(0).setPreferredWidth(40);
		tableAvMovies.getColumnModel().getColumn(1).setPreferredWidth(85);
		tableAvMovies.getColumnModel().getColumn(2).setPreferredWidth(50);
		tableAvMovies.getColumnModel().getColumn(3).setPreferredWidth(40);
		tableAvMovies.getColumnModel().getColumn(4).setPreferredWidth(85);
	
		TableColumnModel columnModel = tableAvMovies.getColumnModel();
		TableColumn column = columnModel.getColumn(5);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		column.setWidth(0);
		column.setPreferredWidth(0);
		TableColumn column6 = columnModel.getColumn(6);
		column6.setMinWidth(0);
		column6.setMaxWidth(0);
		column6.setWidth(0);
		column6.setPreferredWidth(0);

		scrollPane.setViewportView(tableAvMovies);
		tableAvMovies.setBackground(new Color(139, 0, 0));
		
		updateMovieTable();
		
		tableAvMovies.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent event) {
		        // Check if the row selection is changing and not being cleared
		        if (!event.getValueIsAdjusting() && tableAvMovies.getSelectedRow() != -1) {
		            // Get the selected row index
		            int selectedRow = tableAvMovies.getSelectedRow();
		            
		            ((SpinnerNumberModel) spinnerTicketQuantity.getModel()).addChangeListener(new ChangeListener() {
		                public void stateChanged(ChangeEvent e) {
		                    int selectedRow = tableAvMovies.getSelectedRow();

		                    if (selectedRow >= 0 && selectedRow < tableAvMovies.getRowCount()) {
		                        String priceString = getValueFromSelectedRow(selectedRow, 5);
		                        double price = Double.parseDouble(priceString);

		                        int ticketsQuantity = (int) spinnerTicketQuantity.getValue();
		                        double totalPayment = price * ticketsQuantity;
		                        updateLabel(lblTOTAL, String.format("%.2f", totalPayment));
		                    }
		                }
		            });

		            // Check if the selected row is within the valid range
		            if (selectedRow >= 0 && selectedRow < tableAvMovies.getRowCount()) {
		                // Retrieve data from the selected row
		                String title = getValueFromSelectedRow(selectedRow, 1);
		                String cinema = getValueFromSelectedRow(selectedRow, 0);
		                String showDate = getValueFromSelectedRow(selectedRow, 4);
		                String priceString = getValueFromSelectedRow(selectedRow, 5);
		                String seatsAvailable = getValueFromSelectedRow(selectedRow, 6);
		                double price = Double.parseDouble(priceString);
		                int seatsAvailableInt = Integer.parseInt(seatsAvailable);
		                ((SpinnerNumberModel) spinnerTicketQuantity.getModel()).setMaximum(seatsAvailableInt);

		                updateLabel(lblTITLE, title);
		                updateLabel(lblSHOWDATE, showDate);
		                updateLabel(lblCINEMA, cinema);
		                updateLabel(lblPRICE, priceString);


		                int ticketsQuantity = (int) spinnerTicketQuantity.getValue();
		                double totalPayment = price * ticketsQuantity;
		                updateLabel(lblTOTAL, String.format("%.2f", totalPayment));
		            }
		        }
		    }

		    // Helper method to safely get the value from the selected row
		    private String getValueFromSelectedRow(int row, int column) {
		        Object value = tableAvMovies.getValueAt(row, column);
		        return (value != null) ? value.toString() : "";
		    }
		});

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 0, 0)));
		panel_1.setBackground(new Color(0, 0, 0));
		panel_1.setBounds(10, 11, 550, 60);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblAvMovies = new JLabel("AVAILABLE MOVIES");
		lblAvMovies.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvMovies.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
		lblAvMovies.setForeground(new Color(255, 69, 0));
		lblAvMovies.setBounds(75, 15, 400, 30);
		panel_1.add(lblAvMovies);
		
		JLabel lblSelectHere = new JLabel("---------------------------------------------------  SELECT MOVIE HERE  ----------------------------------------------------");
		lblSelectHere.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblSelectHere.setForeground(new Color(253, 245, 230));
		lblSelectHere.setBounds(10, 75, 550, 14);
		contentPane.add(lblSelectHere);
		
		JPanel panel_2 = new JPanel();
		panel_2.setForeground(new Color(255, 69, 0));
		panel_2.setBackground(new Color(0, 0, 0));
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 0, 0)));
		panel_2.setBounds(570, 11, 270, 350);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblBookingInfo = new JLabel("---- Booking Information ----");
		lblBookingInfo.setBackground(new Color(178, 34, 34));
		lblBookingInfo.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblBookingInfo.setForeground(new Color(253, 245, 230));
		lblBookingInfo.setBounds(45, 20, 200, 16);
		panel_2.add(lblBookingInfo);
		
		JLabel lblTitle = new JLabel("Movie Title:");
		lblTitle.setForeground(new Color(253, 245, 230));
		lblTitle.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblTitle.setBounds(20, 53, 90, 16);
		panel_2.add(lblTitle);
		
		JLabel lblCinema = new JLabel("Cinema:");
		lblCinema.setForeground(new Color(253, 245, 230));
		lblCinema.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblCinema.setBounds(20, 91, 90, 16);
		panel_2.add(lblCinema);
		
		JLabel lblShowtime = new JLabel("Showtime");
		lblShowtime.setForeground(new Color(253, 245, 230));
		lblShowtime.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblShowtime.setBounds(20, 127, 90, 16);
		panel_2.add(lblShowtime);
		
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		spinnerTicketQuantity = new JSpinner(spinnerModel);
		((JSpinner.DefaultEditor) spinnerTicketQuantity.getEditor()).getTextField().setEditable(false); // Make the spinner not editable
		spinnerTicketQuantity.setBounds(150, 195, 75, 30);
		panel_2.add(spinnerTicketQuantity);

		JLabel lblTickets = new JLabel("Tickets:");
		lblTickets.setForeground(new Color(253, 245, 230));
		lblTickets.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblTickets.setBounds(80, 205, 90, 16);
		panel_2.add(lblTickets);
		
		JLabel lbldash = new JLabel("--------------------------------------------");
		lbldash.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbldash.setForeground(new Color(255, 255, 255));
		lbldash.setBounds(21, 280, 230, 14);
		panel_2.add(lbldash);
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setForeground(new Color(255, 69, 0));
		lblTotal.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblTotal.setBounds(80, 250, 90, 16);
		panel_2.add(lblTotal);
		
		JButton btnBuyTickets = new JButton("BUY TICKETS");
		btnBuyTickets.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Get information from labels
		        String title = lblTITLE.getText();

		        // Check if movie title is not empty or null
		        if (title == null || title.trim().isEmpty()) {
		            // Display an error message
		            JOptionPane.showMessageDialog(CustomerFrame.this, "You Haven't Selected a Movie", "Error", JOptionPane.ERROR_MESSAGE);
		            return; // Stop further processing
		        }

		        // Continue with creating the receipt message
		        String showDate = lblSHOWDATE.getText();
		        int ticketQuantity = (int) spinnerTicketQuantity.getValue();
		        String cinema = lblCINEMA.getText();
		        String price = lblPRICE.getText();
		        String totalPayment = lblTOTAL.getText();

		        // Display a message with the information
		        String message = String.format(
		            "Movie Title: %s\nCinema: %s\nShowtime: %s\nTicket Quantity: %d\nPrice per Ticket: %s\nTotal Payment: %s",
		            title, cinema, showDate, ticketQuantity, price, totalPayment
		        );

		        // Show the message using JOptionPane
		        JOptionPane.showMessageDialog(CustomerFrame.this, message, "Receipt", JOptionPane.INFORMATION_MESSAGE);

		        lblTITLE.setText("");
		        lblSHOWDATE.setText("");
		        lblCINEMA.setText("");
		        lblPRICE.setText("");
		        lblTOTAL.setText("");
		        spinnerTicketQuantity.setValue(1);
		        
		        updateSeatsAvailable(tableAvMovies.getSelectedRow(), ticketQuantity);


		    }
		});

		btnBuyTickets.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnBuyTickets.setBackground(new Color(255, 99, 71));
		btnBuyTickets.setForeground(new Color(255, 255, 0));
		btnBuyTickets.setBounds(34, 300, 200, 30);
		panel_2.add(btnBuyTickets);
		
		lblTITLE = new JLabel("");
		lblTITLE.setForeground(new Color(255, 255, 255));
		lblTITLE.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblTITLE.setBackground(new Color(255, 255, 255));
		lblTITLE.setBounds(91, 50, 170, 25);
		panel_2.add(lblTITLE);
		
		lblSHOWDATE = new JLabel("");
		lblSHOWDATE.setVerticalAlignment(SwingConstants.BOTTOM);
		lblSHOWDATE.setForeground(new Color(255, 255, 255));
		lblSHOWDATE.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblSHOWDATE.setBackground(Color.WHITE);
		lblSHOWDATE.setBounds(91, 115, 170, 25);
		panel_2.add(lblSHOWDATE);
		
		lblPRICE = new JLabel("");
		lblPRICE.setVerticalAlignment(SwingConstants.BOTTOM);
		lblPRICE.setForeground(new Color(255, 255, 255));
		lblPRICE.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblPRICE.setBackground(Color.WHITE);
		lblPRICE.setBounds(91, 151, 170, 25);
		panel_2.add(lblPRICE);
		
		lblTOTAL = new JLabel("");
		lblTOTAL.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTOTAL.setForeground(new Color(255, 255, 255));
		lblTOTAL.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblTOTAL.setBackground(Color.WHITE);
		lblTOTAL.setBounds(148, 240, 75, 25);
		panel_2.add(lblTOTAL);
		
		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setForeground(new Color(253, 245, 230));
		lblPrice.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblPrice.setBounds(20, 162, 90, 16);
		panel_2.add(lblPrice);
		
		lblCINEMA = new JLabel("");
		lblCINEMA.setVerticalAlignment(SwingConstants.BOTTOM);
		lblCINEMA.setForeground(Color.WHITE);
		lblCINEMA.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblCINEMA.setBackground(Color.WHITE);
		lblCINEMA.setBounds(91, 81, 90, 25);
		panel_2.add(lblCINEMA);
		
		JLabel lblTicketIcon = new JLabel("");
		lblTicketIcon.setIcon(new ImageIcon(OpeningFrame.class.getResource("/images/ticket (1).png")));
		lblTicketIcon.setBounds(640, 375, 135, 100);
		contentPane.add(lblTicketIcon);
		
		JButton btnHome = new JButton("");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openingFrame = new OpeningFrame();
				openingFrame.setVisible(true);
				CustomerFrame.this.dispose();
				
			}
		});
		btnHome.setForeground(SystemColor.inactiveCaptionText);
		btnHome.setBackground(SystemColor.inactiveCaptionText);
		btnHome.setIcon(new ImageIcon(OpeningFrame.class.getResource("/images/home-page.png")));
		btnHome.setBounds(800, 437, 40, 40);
		contentPane.add(btnHome);
	}
	protected String getValueFromSelectedRow(int selectedRow, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	private Properties loadMovieData() {
	    Properties loadedProperties = new Properties();

	    try (FileInputStream input = new FileInputStream(PROPERTIES_FILE)) {
	        loadedProperties.load(input);
	    } catch (IOException e) {
	        e.printStackTrace();
	        // Handle the exception (e.g., show an error message) or return an empty Properties
	        return new Properties();
	    }

	    return loadedProperties;
	}

	private void updateMovieTable() {
	    DefaultTableModel tableModel = (DefaultTableModel) tableAvMovies.getModel();
	    tableModel.setRowCount(0); // Clear existing rows

	    int i = 0;
	    while (movieProperties.containsKey("Title" + i)) {
	    	String cinema = movieProperties.getProperty("Cinema" + i);
	    	String title = movieProperties.getProperty("Title" + i);
	        String genre = movieProperties.getProperty("Genre" + i);
	        String duration = movieProperties.getProperty("Duration" + i);
	        String showDate = movieProperties.getProperty("ShowDate" + i);
	        String showtime = movieProperties.getProperty("Showtime" + i);
	        String price = movieProperties.getProperty("Price" + i);
	        String seatsAvailable = movieProperties.getProperty("SeatsAvailable" + i);

	        // Check if SeatsAvailable is not 0 or null
	        if (seatsAvailable != null && !seatsAvailable.trim().isEmpty() && !seatsAvailable.equals("0")) {
	            // Add data to the table model
	            tableModel.addRow(new Object[]{cinema, title, genre, duration, showDate + " " + showtime, price, seatsAvailable});
	        }

	        i++;
	    }
	}
    
    private void updateSeatsAvailable(int selectedRow, int ticketsBought) {
        if (selectedRow >= 0 && selectedRow < tableAvMovies.getRowCount()) {
            // Get the current SeatsAvailable value
            String seatsAvailableString = movieProperties.getProperty("SeatsAvailable" + selectedRow);
            int currentSeatsAvailable = Integer.parseInt(seatsAvailableString);

            // Update the SeatsAvailable property
            int newSeatsAvailable = currentSeatsAvailable - ticketsBought;
            movieProperties.setProperty("SeatsAvailable" + selectedRow, String.valueOf(newSeatsAvailable));

                // Save the updated properties to the file
            saveMovieData();
            updateMovieTable();
        
        }
    }

    private void saveMovieData() {
        try (FileOutputStream output = new FileOutputStream(PROPERTIES_FILE)) {
            movieProperties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message) if saving fails
        }
    }

    
    private void updateLabel(JLabel label, String text) {
        if (label != null) {
            label.setText(text);
        }
    }
}
