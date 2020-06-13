package simulationjava;

/*
THIS CODE IS MY OWN WORK. I DID NOT SEARCH FOR SOLUTION, or I DID NOT CONSULT TO ANY  PROGRAM WRITTEN BY OTHER STUDENTS or DID NOT COPY ANY PROGRAM FROM OTHER SOURCES. 
I READ AND FOLLOWED THE GUIDELINE GIVEN IN THE PROGRAMMING ASSIGNMENT. NAME: BURAK YILDIRIM
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class SimulationFrame {
	static int max_sim_input, max_arr_input, max_serv_input, timer_speed_input, instant_second;
	JPanel p;
	JPanel p2;
	JFrame f;
	JFrame f2;
	JLabel max_sim_label, max_serv_label, max_arr_label, t_speed;
	JLabel sim_time, sim_time_number, q_length, q_length_number, total_serv_time, total_serv_time_number;
	JButton start_button;
	JTextField max_sim_text, max_arr_text, max_serv_text;
	JOptionPane msg;
	JComboBox<String> timer_speed;
	
	static Cashier cashier;
	static CustomerQueue queue;

	Customer customer_getting_serviced = null, new_customer, head_customer;

	//creating flags
	boolean custom_deleted = true, cashier_working = false;

	static int next_arr;

	//constructor of simulationframe class
	public SimulationFrame() {
		first_display();
	}

	//method for creating the first screen
	private void first_display() {
		
		//millisecond options
		final String millisecond_list[] = {"200", "400", "500", "1000"};
		p = new JPanel();
		f = new JFrame();
		timer_speed = new JComboBox<>(millisecond_list);
		t_speed = new JLabel("Timer Speed (Milliseconds)");
		max_sim_label = new JLabel("Max Simulation Time: ");
		max_arr_label = new JLabel("Max Arrival Time: ");
		max_serv_label = new JLabel("Max Service Time: ");
		start_button = new JButton();
		start_button.setText("Start Simulation");
		msg = new JOptionPane();
		max_sim_text = new JTextField(20);
		max_arr_text = new JTextField(20);
		max_serv_text = new JTextField(20);
		

		//Determining the layout and border for the panel
		p.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
		p.setLayout(new GridLayout(5, 2));

		p.add(max_sim_label);
		p.add(max_sim_text);
		p.add(max_arr_label);
		p.add(max_arr_text);
		p.add(max_serv_label);
		p.add(max_serv_text);
		p.add(t_speed);
		p.add(timer_speed);
		p.add(start_button);
		
		f.add(p, BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("COMP:132 HW2");
		f.setSize(500, 300);
		f.pack();
		f.setVisible(true);

		// Set action listener for the button
		start_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				try {
					max_sim_input = Integer.parseInt(max_sim_text.getText());
					max_arr_input = Integer.parseInt(max_arr_text.getText());
					max_serv_input = Integer.parseInt(max_serv_text.getText());
					timer_speed_input = Integer.parseInt(millisecond_list[timer_speed.getSelectedIndex()]);
					f.dispose();
					second_display();
				} catch (final NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Please enter valid information!");
				}

			}

		});
	}

	//method for creating the second screen
	private void second_display() {

		p2 = new JPanel();
		f2 = new JFrame();
		sim_time = new JLabel("Current Simulation Time:	");
		sim_time_number = new JLabel();
		q_length = new JLabel("Current Queue Lenght: 	");
		q_length_number = new JLabel();
		total_serv_time = new JLabel("Total Service Time: 	");
		total_serv_time_number = new JLabel();

		// Setting the border and layout for the second panel
		p2.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
		p2.setLayout(new GridLayout(3, 2));

		p2.add(sim_time);
		p2.add(sim_time_number);
		p2.add(q_length);
		p2.add(q_length_number);
		p2.add(total_serv_time);
		p2.add(total_serv_time_number);
		

		f2.add(p2, BorderLayout.CENTER);
		f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f2.setSize(500, 300);
		f2.pack();
		f2.setVisible(true);

		start_timer(sim_time_number, q_length_number, total_serv_time_number);

	}

	private void start_timer(JLabel sim_time_number, JLabel q_length_number , JLabel total_serv_time_number) {

		
		queue = new CustomerQueue();
		cashier = new Cashier();
		head_customer = new_customer();
		queue.enqueue(head_customer);
		next_arr = head_customer.custom_arr_t;

		//setting the flags
		custom_deleted = true;
		cashier_working = false;

		ActionListener count = new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				if (instant_second >= max_sim_input) {
					//When simulation ends adding remaining customer's wait time in the queue to the total wait time
					for (Customer c : CustomerQueue.c_queue) {
						queue.total_wait_t += c.wait_t;
					}
					//prepare the statistics
					String stats = "Number of Customers: " + Cashier.total_custom + "\nAverage Wait Time: "
							+ queue.find_average_wait() + "\nAverage Service Time: " + cashier.find_average_serv_t()
							+ "\nMaximum Wait Time: " + queue.max_wait_t + "\nMaximum Queue Length: " + queue.max_custom_line;
					f2.dispose();
					JOptionPane.showMessageDialog(null, stats);
					System.exit(0);
				} else {
					sim_time_number.setText("" + instant_second);
					instant_second++;
					int size = CustomerQueue.c_queue.size();
					q_length_number.setText("" + size);
					total_serv_time_number.setText("" + Cashier.total_serv_t);
					
					
					//create new customer and get into the queue when its time comes 
					if (instant_second >= next_arr) {
						new_customer = new_customer();
						queue.enqueue(new_customer);
						next_arr = new_customer.custom_arr_t;
					}
					
					//let head customer getting served when cashier isn't working, queue isn't empty and customer arrived
					if (!cashier_working && !(CustomerQueue.c_queue.size()==0)) {

						head_customer = CustomerQueue.c_queue.get(0);
						if (head_customer.customer_arrived(instant_second)) {
							cashier_working = true;
							customer_getting_serviced = head_customer;
						}
					}
					
					//let the cashier service to the customer when queue isn't empty and cashier is working
					if (cashier_working && !(CustomerQueue.c_queue.size()==0)) {

						cashier.do_service(customer_getting_serviced);

						if (cashier.serve_complete) {
							cashier_working = false;
						} else {
							cashier_working = true;
						}
							custom_deleted = false;
						
					}
					
					//when customer's work is done dequeue customer from the queue
					if (!cashier_working && !custom_deleted) {
						queue.dequeue();
						custom_deleted = true;
					}
					
					
					//increase the wait time of all customers except the first
					queue.add_queue_wait();
				}

			}
		};
		//setting up the timer
		Timer timer = new Timer(timer_speed_input, count);
		timer.setInitialDelay(0);
		timer.start();
		instant_second = 0;
	}
	
	
	
	//method for generating the arrival time
	public static int generate_arr_t() {
			Random rgen = new Random();
			return rgen.nextInt(max_arr_input) + 1;
	}
		
	//method for generating the service time
	public static int generate_serv_t() {
		Random rgen = new Random();
		return rgen.nextInt(max_serv_input) + 1;
	}

	
	
	//method for creating new customer
	public static Customer new_customer() {
			Customer c = new Customer(generate_arr_t() + instant_second);
			c.serv_t = generate_serv_t();
			Cashier.total_serv_t += c.serv_t;
			Cashier.total_custom++;
			return c;
	}
}






