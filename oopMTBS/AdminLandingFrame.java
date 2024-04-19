package oopMTBS;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Rectangle;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class AdminLandingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableMovies;
	private JTextField textFieldTitle;
	private JTextField textFieldDuration;
	private JTextField textFieldShowdate;
	private JTextField textFieldPrice;
	private JLabel lblGenre;
	private JLabel lblDuration;
	private JLabel lblShowdate;
	private JLabel lblSeatsAvailable;
	private JLabel lblPrice;
	private JButton btnClearFields;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JComboBox comboBoxShowtime;
	private JComboBox comboBoxCinema;
	private JComboBox comboBoxGenre;
	private OpeningFrame openingFrame;
	private DefaultTableModel tableModel;
	private List<Movie> movieList;
	private static final String PROPERTIES_FILE = "movieData.properties";



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminLandingFrame frame = new AdminLandingFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminLandingFrame() {
		setResizable(false);
		setBounds(new Rectangle(0, 0, 1000, 1000));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 865, 547);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaptionText);
		panel.setBounds(250, 50, 600, 460);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		scrollPane.setBackground(SystemColor.inactiveCaptionText);
		scrollPane.setBounds(0, 0, 600, 460);
		panel.add(scrollPane);
		
		tableMovies = new JTable();
		tableMovies.setForeground(new Color(245, 245, 220));
		tableMovies.setBackground(SystemColor.inactiveCaptionText);
		tableMovies.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Cinema", "Title", "Genre", "Duration", "Show Date", "Seats Available", "Price"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, Integer.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableMovies.getColumnModel().getColumn(0).setPreferredWidth(44);
		tableMovies.getColumnModel().getColumn(1).setPreferredWidth(88);
		tableMovies.getColumnModel().getColumn(2).setPreferredWidth(58);
		tableMovies.getColumnModel().getColumn(3).setPreferredWidth(60);
		tableMovies.getColumnModel().getColumn(4).setPreferredWidth(90);
		tableMovies.getColumnModel().getColumn(5).setPreferredWidth(77);
		tableMovies.getColumnModel().getColumn(6).setPreferredWidth(35);
		scrollPane.setViewportView(tableMovies);
		
		tableMovies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		movieList = new ArrayList<>();
		loadMovieData();
		updateTable();

		
		tableMovies.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent event) {
		        // Check if the row selection is changing and not being cleared
		        if (!event.getValueIsAdjusting() && tableMovies.getSelectedRow() != -1) {
		            // Get the selected row index
		            int selectedRow = tableMovies.getSelectedRow();

		            // Check if the selected row is within the valid range
		            if (selectedRow >= 0 && selectedRow < tableMovies.getRowCount()) {
		                // Retrieve data from the selected row
		                String cinema = getValueFromSelectedRow(selectedRow, 0);
		                String title = getValueFromSelectedRow(selectedRow, 1);
		                String genre = getValueFromSelectedRow(selectedRow, 2);
		                String duration = getValueFromSelectedRow(selectedRow, 3);
		                String showDateTime = getValueFromSelectedRow(selectedRow, 4);
		                String price = getValueFromSelectedRow(selectedRow, 6);

		                // Split showDateTime into showdate and showtime
		                String[] dateTimeParts = showDateTime.split(" ");
		                String showdate = dateTimeParts[0];
		                String showtime = dateTimeParts[1];

		                // Update the text fields with the selected row data
		                textFieldTitle.setText(title);
		                comboBoxGenre.setSelectedItem(genre);
		                textFieldDuration.setText(duration);
		                textFieldShowdate.setText(showdate);
		                comboBoxCinema.setSelectedItem(cinema);
		                comboBoxShowtime.setSelectedItem(showtime);
		                textFieldPrice.setText(price);
		            }
		        }
		    }

		});

		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(204, 51, 0)));
		panel_1.setBackground(SystemColor.inactiveCaptionText);
		panel_1.setBounds(0, 0, 250, 510);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblAdminIcon = new JLabel("New label");
		lblAdminIcon.setBounds(20, 400, 67, 67);
		lblAdminIcon.setIcon(new ImageIcon(OpeningFrame.class.getResource("/images/circle (1).png")));
		panel_1.add(lblAdminIcon);
		
		JButton btnLogout = new JButton("Log Out");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openingFrame = new OpeningFrame();
		        openingFrame.setVisible(true);
		        AdminLandingFrame.this.dispose();
			}
		});
		btnLogout.setForeground(new Color(240, 255, 240));
		btnLogout.setBackground(new Color(178, 34, 34));
		btnLogout.setBounds(97, 410, 120, 40);
		panel_1.add(btnLogout);
		
		textFieldTitle = new JTextField();
		textFieldTitle.setBounds(84, 52, 150, 30);
		textFieldTitle.setColumns(10);
		panel_1.add(textFieldTitle);
		
		textFieldDuration = new JTextField();
		textFieldDuration.setBounds(84, 134, 150, 30);
		textFieldDuration.setColumns(10);
		panel_1.add(textFieldDuration);
		
		textFieldShowdate =  new JTextField();
		textFieldShowdate.setBounds(84, 175, 65, 30);
		textFieldShowdate.setColumns(10);
		panel_1.add(textFieldShowdate);
		
		JLabel lblMovieInfo = new JLabel("Movie Information\r\n");
		lblMovieInfo.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblMovieInfo.setBounds(45, 19, 170, 14);
		lblMovieInfo.setForeground(new Color(255, 255, 255));
		panel_1.add(lblMovieInfo);
		
		textFieldPrice = new JTextField();
		textFieldPrice.setColumns(10);
		textFieldPrice.setBounds(109, 257, 125, 30);
		textFieldPrice.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c))) {
                    e.consume();
                }
            }
        });
		panel_1.add(textFieldPrice);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblTitle.setForeground(SystemColor.textHighlightText);
		lblTitle.setBounds(20, 60, 46, 14);
		panel_1.add(lblTitle);
		
		lblGenre = new JLabel("Genre:");
		lblGenre.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblGenre.setForeground(SystemColor.textHighlightText);
		lblGenre.setBounds(20, 101, 46, 14);
		panel_1.add(lblGenre);
		
		lblDuration = new JLabel("Duration:");
		lblDuration.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblDuration.setForeground(SystemColor.textHighlightText);
		lblDuration.setBounds(20, 142, 55, 14);
		panel_1.add(lblDuration);
		
		lblShowdate = new JLabel("Show Time:");
		lblShowdate.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblShowdate.setForeground(SystemColor.textHighlightText);
		lblShowdate.setBounds(20, 183, 60, 14);
		panel_1.add(lblShowdate);
		
		lblSeatsAvailable = new JLabel("Cinema:");
		lblSeatsAvailable.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblSeatsAvailable.setForeground(SystemColor.textHighlightText);
		lblSeatsAvailable.setBounds(20, 224, 67, 14);
		panel_1.add(lblSeatsAvailable);
		
		lblPrice = new JLabel("Price:");
		lblPrice.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblPrice.setForeground(SystemColor.textHighlightText);
		lblPrice.setBounds(20, 265, 46, 14);
		panel_1.add(lblPrice);
		
		JButton btnAdd = new JButton("Add ");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addMovie();
		    }
		});
		btnAdd.setForeground(new Color(250, 250, 210));
		btnAdd.setBackground(new Color(139, 0, 0));
		btnAdd.setBounds(20, 310, 105, 30);
		panel_1.add(btnAdd);
		
		btnClearFields = new JButton("Clear Fields");
		btnClearFields.setForeground(new Color(250, 250, 210));
		btnClearFields.setBackground(new Color(139, 0, 0));
		btnClearFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearInputFields();
			}
		});
		btnClearFields.setBounds(125, 310, 105, 30);
		panel_1.add(btnClearFields);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateMovie();
		    }
		});
			
		btnUpdate.setBackground(new Color(139, 0, 0));
		btnUpdate.setForeground(new Color(253, 245, 230));
		btnUpdate.setBounds(20, 341, 105, 30);
		panel_1.add(btnUpdate);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMovie();
			}
		});
		btnDelete.setForeground(new Color(253, 245, 230));
		btnDelete.setBackground(new Color(139, 0, 0));
		btnDelete.setBounds(125, 341, 105, 30);
		panel_1.add(btnDelete);
		
		comboBoxShowtime = new JComboBox();
		comboBoxShowtime.setBackground(new Color(255, 255, 255));
		comboBoxShowtime.setModel(new DefaultComboBoxModel(new String[] {"9:00 am", "11:00 am", "01:00 pm", "03:00 pm", "05:00 pm", "07:00 pm", "09:00 pm", "11:00 pm"}));
		comboBoxShowtime.setBounds(153, 175, 81, 30);
		panel_1.add(comboBoxShowtime);
		
		comboBoxCinema = new JComboBox();
		comboBoxCinema.setBackground(new Color(255, 255, 255));
		comboBoxCinema.setModel(new DefaultComboBoxModel(new String[] {"Cinema 1", "Cinema 2", "Cinema 3"}));
		comboBoxCinema.setBounds(84, 216, 150, 30);
		panel_1.add(comboBoxCinema);
		
		comboBoxGenre = new JComboBox();
		comboBoxGenre.setModel(new DefaultComboBoxModel(new String[] {"Action", "Adventure", "Animation", "Comedy", "Drama", "Family", "Fantasy", "Horror", "Mystery", "Romance", "Sci-Fi", "Thriller"}));
		comboBoxGenre.setBackground(Color.WHITE);
		comboBoxGenre.setBounds(84, 93, 150, 30);
		panel_1.add(comboBoxGenre);
		
		JLabel lblMovieInformation = new JLabel("M O V I E S   I N F O R M A T I O N");
		lblMovieInformation.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 44));
		lblMovieInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblMovieInformation.setForeground(new Color(255, 99, 71));
		lblMovieInformation.setBounds(260, 9, 547, 40);
		contentPane.add(lblMovieInformation);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveMovieData();
            }
        });
	}
	
	private void loadMovieData() {
	    Properties properties = new Properties();
	    try (FileInputStream input = new FileInputStream(PROPERTIES_FILE)) {
	        properties.load(input);
	        for (int i = 0; i < Integer.MAX_VALUE; i++) {
	            String title = properties.getProperty("Title" + i);
	            if (title == null) {
	                break;
	            }
	            String genre = properties.getProperty("Genre" + i);
	            String duration = properties.getProperty("Duration" + i);
	            String cinema = properties.getProperty("Cinema" + i);
	            String showDate = properties.getProperty("ShowDate" + i);
	            String showtime = properties.getProperty("Showtime" + i);
	            int seatsAvailable = Integer.parseInt(properties.getProperty("SeatsAvailable" + i));
	            double price = Double.parseDouble(properties.getProperty("Price" + i));
	            movieList.add(new Movie(title, genre, duration, cinema, showDate, showtime, seatsAvailable, price));
	        }
	    } catch (IOException | NumberFormatException e) {
	        // Handle the case where the file doesn't exist
	        e.printStackTrace();

	        // Create a new properties file
	        createEmptyPropertiesFile();
	    }
	}

	private void createEmptyPropertiesFile() {
	    Properties properties = new Properties();
	    try (FileOutputStream output = new FileOutputStream(PROPERTIES_FILE)) {
	        properties.store(output, null);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	private void saveMovieData() {
	    Properties properties = new Properties();
	    for (int i = 0; i < movieList.size(); i++) {
	        Movie movie = movieList.get(i);
	        properties.setProperty("Title" + i, movie.getTitle());
	        properties.setProperty("Genre" + i, movie.getGenre());
	        properties.setProperty("Duration" + i, movie.getDuration());
	        properties.setProperty("Cinema" + i, movie.getCinema());
	        properties.setProperty("ShowDate" + i, movie.getShowDate());
	        properties.setProperty("Showtime" + i, movie.getShowtime());
	        properties.setProperty("SeatsAvailable" + i, String.valueOf(movie.getSeatsAvailable()));
	        properties.setProperty("Price" + i, String.valueOf(movie.getPrice()));
	    }
	    try (FileOutputStream output = new FileOutputStream(PROPERTIES_FILE)) {
	        properties.store(output, null);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	private void clearInputFields() {
        textFieldTitle.setText("");
        comboBoxGenre.setSelectedItem("Action");
        textFieldDuration.setText("");
        textFieldShowdate.setText("");
        textFieldPrice.setText("");
        comboBoxShowtime.setSelectedItem("09:00 am");
        comboBoxCinema.setSelectedItem("Cinema 1");
        
    }
	
	private void addMovie() {
	    // Retrieve data from text fields
	    String title = textFieldTitle.getText().trim();
	    String duration = textFieldDuration.getText().trim();
	    String showDate = textFieldShowdate.getText().trim();
	    String priceString = (textFieldPrice.getText());


	    // Check if any of the required fields is empty
	    if (title.isEmpty() || duration.isEmpty() || showDate.isEmpty() || priceString.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    // Retrieve data from combo boxes
	    String genre = comboBoxGenre.getSelectedItem().toString();
	    String cinema = comboBoxCinema.getSelectedItem().toString();
	    String showtime = comboBoxShowtime.getSelectedItem().toString();

	    // Initialize seatsAvailable with a default value
	    int seatsAvailable = 0;

	    // Set seatsAvailable based on the selected cinema
	    if (cinema.equals("Cinema 1")) {
	        seatsAvailable = 230;
	    } else if (cinema.equals("Cinema 2")) {
	        seatsAvailable = 200;
	    } else if (cinema.equals("Cinema 3")) {
	        seatsAvailable = 120;
	    } 

	    // Convert price to Double
	    double price = Double.parseDouble(priceString);

	    // Create a new Movie instance
	    Movie movie = new Movie(title, genre, duration, cinema, showDate, showtime, seatsAvailable, price);

	    // Add the movie to the list
	    movieList.add(movie);

	    // Update the table
	    updateTable();
	    saveMovieData();
	}

	
	private void updateMovie() {
	    int selectedRow = tableMovies.getSelectedRow();

	    // Check if a row is selected
	    if (selectedRow != -1) {
	        // Get updated data from the input fields
	        Movie selectedMovie = movieList.get(selectedRow);

	        selectedMovie.setTitle(textFieldTitle.getText());
	        selectedMovie.setGenre(comboBoxGenre.getSelectedItem().toString());
	        selectedMovie.setDuration(textFieldDuration.getText());
	        selectedMovie.setShowDate(textFieldShowdate.getText());
	        selectedMovie.setShowtime((String) comboBoxShowtime.getSelectedItem());
	        selectedMovie.setPrice(Double.parseDouble(textFieldPrice.getText()));

	        

	        String seatsAvailableString = getValueFromSelectedRow(selectedRow, 5);
	        int seatsAvailable = Integer.parseInt(seatsAvailableString);
	        
	        if (seatsAvailable == 0) {
	            // Allow the user to update cinema
	            String cinema = comboBoxCinema.getSelectedItem().toString();
	            comboBoxCinema.setSelectedItem(cinema);
	            if (cinema.equals("Cinema 1")) {
	    	        seatsAvailable = 250;
	    	    } else if (cinema.equals("Cinema 2")) {
	    	        seatsAvailable = 200;
	    	    } else if (cinema.equals("Cinema 3")) {
	    	        seatsAvailable = 120;
	    	    }
	            selectedMovie.setSeatsAvailable(seatsAvailable);

	        } 
	        
	        // Update the table
	        updateTable();
	        saveMovieData();

	        // Clear input fields
	        clearInputFields();
	    } else {
	        JOptionPane.showMessageDialog(null, "No row selected.");
	    }
	}



    private void deleteMovie() {
        int selectedRow = tableMovies.getSelectedRow();

        // Check if a row is selected
        if (selectedRow != -1) {
            // Remove the selected movie from the list
            movieList.remove(selectedRow);

            // Update the table
            updateTable();
            saveMovieData();

            // Clear the text fields after removing a row
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(null, "No row selected.");
        }
    }

    private void updateTable() {
        // Sort movieList based on title (case-insensitive)
        movieList.sort(Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER));

        // Clear existing rows
        DefaultTableModel model = (DefaultTableModel) tableMovies.getModel();
        model.setRowCount(0);

        // Add movies to the table
        for (Movie movie : movieList) {
            Object[] rowData = { movie.getCinema(), movie.getTitle(), movie.getGenre(), movie.getDuration(),
                    movie.getShowDate() + " " + movie.getShowtime(), movie.getSeatsAvailable(), movie.getPrice() };
            model.addRow(rowData);
        }
    }


    
    private String getValueFromSelectedRow(int row, int column) {
        Object value = tableMovies.getValueAt(row, column);
        return (value != null) ? value.toString() : "";
    }
}

