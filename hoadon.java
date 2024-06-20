package banghoadon;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
//import javax.swing.JInternalFrame;
//import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
//import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.HashMap;
//import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
//import java.beans.PropertyChangeListener;
//import java.io.InputStream;
//import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class hoadon extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField phone_noTextField;
	private JTextField pro_codeTextField;
	private JTextField pro_unitTextField;
	private JTextField pro_priceTextField;
	private JTextField pro_quantityTextField;
	private JTextField invoiceDateTextField;
	private JTextField invoice_noTextField;
	private JTextField addressTextField;
	private JTextField total_priceTextField;
	private JTextField taxTextField;
	private JTextField total_amountTextField;
	private JTextField total_costprice;
	private JTextField customer_code;
	private JLabel lblMKhchHng;
	private JLabel pro_totalprice;
	
	private void tableColumnSize() {
		table_sale.getColumnModel().getColumn(0).setPreferredWidth(10);
		table_sale.getColumnModel().getColumn(1).setPreferredWidth(60);
		table_sale.getColumnModel().getColumn(2).setPreferredWidth(60);
		table_sale.getColumnModel().getColumn(3).setPreferredWidth(60);
		table_sale.getColumnModel().getColumn(4).setPreferredWidth(60);
		table_sale.getColumnModel().getColumn(5).setPreferredWidth(60);
		table_sale.getColumnModel().getColumn(6).setPreferredWidth(60);
	}
	
	private DefaultTableModel model_sale= new DefaultTableModel();
	private JTable table_sale= new JTable();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					hoadon frame = new hoadon();
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
	
	ResultSet set_com;
	Connection connection = null;
	
	private JComboBox<String> productName;
	
	public void productDropdown() {
		try {
			connection = ConnectionManager.getConnection();
			Statement st = connection.createStatement();
			set_com = st.executeQuery("select tensp from sanpham");
			while (set_com.next()) {
				productName.addItem(set_com.getString(1));
			}
		} catch (SQLException e) {
			// Handle exception
		} finally {
			try {
				connection.close();
			} catch (Exception e2) {
				// Handle exception
			}
		}
	}
	
	public void getProductDetails() {
		try {
			connection = ConnectionManager.getConnection();
			Statement st = connection.createStatement();
			set_com = st.executeQuery("select * from sanpham where tensp = '"+productName.getSelectedItem().toString()+"'");
			if(set_com.next()) {
				pro_codeTextField.setText(set_com.getString(1));
				pro_unitTextField.setText(set_com.getString(3));
				pro_priceTextField.setText(set_com.getString(4));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch(Exception e2) {
				
			}
			
		}
	}
	
	private JComboBox<String> customer_nameComboBox;
	public void getCusDropDown() {
		//select distinct HOTEN from KHACHHANG
		try {
	      	connection = ConnectionManager.getConnection();
	        Statement st = connection.createStatement();
	        ResultSet set_com = st.executeQuery("select HOTEN from KHACHHANG");
	
	        while(set_com.next()) {
	        	customer_nameComboBox.addItem(set_com.getString(1));
	        }
		} catch (Exception e) {
			e.printStackTrace();

	        } finally {
	        	try {
	        		connection.close();
	        } catch (Exception e2) {
	        	
	        }
	    }
	}
	
	
	
	public void getDetailWithCustomerName() {
		try {
		        connection = ConnectionManager.getConnection();
		        Statement st = connection.createStatement();
		        ResultSet set_com = st.executeQuery("select DCHI, SDT, makh from KHACHHANG where HOTEN='"+customer_nameComboBox.getSelectedItem().toString()+"'");
		        if(set_com.next()) {
			        addressTextField.setText(set_com.getString(1));
			        phone_noTextField.setText(set_com.getString(2));
			        customer_code.setText(set_com.getString(3));
		        }
		} catch (Exception e) {
		        e.printStackTrace();
		        // TODO: handle exception
		} finally {
		        try {
		        connection.close();
		        } catch (Exception e2) {
		        	
		        }
		}
	}
	
	public void getDetailedWithPhoneNum() {
		try {
			connection = ConnectionManager.getConnection();
			Statement st = connection.createStatement();
			ResultSet set_com = st.executeQuery("select hoten, dchi from khachhang where sdt = '"+phone_noTextField.getText().toString()+"'");
			if(set_com.next()) {
				customer_nameComboBox.setSelectedItem(set_com.getString(1));
				addressTextField.setText(set_com.getString(2));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch(Exception e2) {
				
			}
		}
	}
	
	public void getDetailedWithCustomerCode() {
		try {
			connection = ConnectionManager.getConnection();
			Statement st = connection.createStatement();
			ResultSet set_com = st.executeQuery("select hoten, dchi, sdt from khachhang where makh = '"+customer_code.getText().toString()+"'");
			if(set_com.next()) {
				customer_nameComboBox.setSelectedItem(set_com.getString(1));;
				addressTextField.setText(set_com.getString(2));
				phone_noTextField.setText(set_com.getString(3));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch(Exception e2) {
				
			}
		}
	}
	
	private void updateTotalCostPrice() {
		try {
			int quantity = Integer.parseInt(pro_quantityTextField.getText());
			double price = Double.parseDouble(pro_priceTextField.getText());
			double totalCost = quantity * price;
			total_costprice.setText(String.valueOf(totalCost));
		} catch (NumberFormatException e) {
			total_costprice.setText("");
		}
	}
	
	private void addRowToTable() {
		String proCode = pro_codeTextField.getText();
		String proName = productName.getSelectedItem().toString();
		String proUnit = pro_unitTextField.getText();
		String proQuantity = pro_quantityTextField.getText();
		String proPrice = pro_priceTextField.getText();
		String totalCost = total_costprice.getText();

		Object[] row = new Object[7];
		row[0] = table_sale.getRowCount() + 1; // STT
		row[1] = proCode;
		row[2] = proName;
		row[3] = proUnit;
		row[4] = proQuantity;
		row[5] = proPrice;
		row[6] = totalCost;

		model_sale.addRow(row);
	}

	private void insertDataToDatabase() {
		String invoiceNo = invoice_noTextField.getText();
		String invoiceDate = invoiceDateTextField.getText();
		String customerCode = customer_code.getText();
		String proCode = pro_codeTextField.getText();
		String proQuantity = pro_quantityTextField.getText();
		String totalCost = total_costprice.getText();

		try {
			connection = ConnectionManager.getConnection();
			String query = "INSERT INTO HOADON (SOHD, NGHD, MAKH, MASP, SL, TRIGIA) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, invoiceNo);
			ps.setString(2, invoiceDate);
			ps.setString(3, customerCode);
			ps.setString(4, proCode);
			ps.setString(5, proQuantity);
			ps.setString(6, totalCost);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void updateTotalPrice() {
		double totalPrice = 0.0;
		for (int i = 0; i < model_sale.getRowCount(); i++) {
			totalPrice += Double.parseDouble(model_sale.getValueAt(i, 6).toString());
		}
		total_priceTextField.setText(String.valueOf(totalPrice));
		updateTaxAndTotalAmount();
	}

	private void updateTaxAndTotalAmount() {
		try {
			double totalPrice = Double.parseDouble(total_priceTextField.getText());
			double tax = totalPrice * 0.10; // 10% tax
			double totalAmount = totalPrice + tax;
			taxTextField.setText(String.valueOf(tax));
			total_amountTextField.setText(String.valueOf(totalAmount));
		} catch (NumberFormatException e) {
			taxTextField.setText("");
			total_amountTextField.setText("");
		}
	}
	
	public void printInvoice() {
		  // Use the Invoice object to print the invoice
		  System.out.println("**Hóa đơn**"); // "Hóa đơn" means "Invoice" in Vietnamese
		  System.out.println("Tên Khách hàng: " + customer_nameComboBox.getSelectedItem().toString()); // Replace with actual customer name
		  // Print other invoice details (e.g., invoice number, date)
		 
		  // Print invoice totals (subtotal, tax, total)
		  System.out.println("\n**Tổng tiền hàng:** $" + total_priceTextField.getText().toString()); //String.format("%.2f", total_priceTextField.getText().toString()));
		  System.out.println("Thuế GTGT (10%): $" + taxTextField.getText().toString()); //String.format("%.2f", taxTextField.getText().toString()));
		  System.out.println("**Tổng thanh toán:** $" + total_amountTextField.getText().toString()); // String.format("%.2f", total_amountTextField.getText().toString()));
		  System.out.println("\n**Cám ơn quý khách đã mua hàng!**");
		  }
	
//	private void printInvoice() {
//		try {
//			connection = ConnectionManager.getConnection();
//			InputStream reportStream = getClass().getResourceAsStream("/reportTemplate.jasper");
//			Map<String, Object> parameters = new HashMap<>();
//			parameters.put("SOHD", invoice_noTextField.getText());
//			parameters.put("NGHD", invoiceDateTextField.getText());
//			parameters.put("MAKH", customer_code.getText());
//			
//			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
//			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
//			JasperViewer.viewReport(jasperPrint, false);
//		} catch (JRException | SQLException e) {
//			((Throwable) e).printStackTrace();
//		} finally {
//			try {
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	public hoadon() {
		setTitle("PHẦN MỀM HÓA ĐƠN VĂN PHÒNG PHẨM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1029, 635);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane ivoiceItemslayeredPane = new JLayeredPane();
		ivoiceItemslayeredPane.setBounds(16, 6, 989, 147);
		contentPane.add(ivoiceItemslayeredPane);
		ivoiceItemslayeredPane.setLayout(new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		ivoiceItemslayeredPane.add(panel, "name_205696390807794");
		panel.setLayout(null);
		
		customer_nameComboBox = new JComboBox<>();
		customer_nameComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getDetailWithCustomerName();
			}
		});
		customer_nameComboBox.setEditable(true);
		customer_nameComboBox.setBounds(18, 60, 283, 26);
		panel.add(customer_nameComboBox);
		customer_nameComboBox.setSelectedItem("");
		
		productName = new JComboBox<>();
		productName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getProductDetails();
			}
		});
		productName.setEditable(true);
		productName.setBounds(18, 110, 130, 26);
		panel.add(productName);
		productName.setSelectedItem("");
		
		phone_noTextField = new JTextField();
		phone_noTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					getDetailedWithPhoneNum();
				}
			}
		});
		phone_noTextField.setBounds(171, 17, 130, 26);
		panel.add(phone_noTextField);
		phone_noTextField.setColumns(10);
		
		pro_codeTextField = new JTextField();
		pro_codeTextField.setColumns(10);
		pro_codeTextField.setBounds(171, 110, 130, 26);
		panel.add(pro_codeTextField);
		
		pro_unitTextField = new JTextField();
		pro_unitTextField.setColumns(10);
		pro_unitTextField.setBounds(326, 111, 136, 26);
		panel.add(pro_unitTextField);
		
		pro_priceTextField = new JTextField();
		pro_priceTextField.setColumns(10);
		pro_priceTextField.setBounds(489, 110, 130, 26);
		panel.add(pro_priceTextField);
		
		pro_quantityTextField = new JTextField();
		pro_quantityTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateTotalCostPrice();
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					addRowToTable();
					insertDataToDatabase();
					updateTotalPrice();
				}
			}
		});
		pro_quantityTextField.setColumns(10);
		pro_quantityTextField.setBounds(646, 110, 130, 26);
		panel.add(pro_quantityTextField);
		
		invoiceDateTextField = new JTextField();
		invoiceDateTextField.setColumns(10);
		invoiceDateTextField.setBounds(646, 19, 130, 26);
		panel.add(invoiceDateTextField);
		
		invoice_noTextField = new JTextField();
		invoice_noTextField.setColumns(10);
		invoice_noTextField.setBounds(805, 19, 165, 26);
		panel.add(invoice_noTextField);
		
		addressTextField = new JTextField();
		addressTextField.setColumns(10);
		addressTextField.setBounds(326, 16, 293, 70);
		panel.add(addressTextField);
		
		JLabel customer_nameLabel = new JLabel("Tên Khách hàng");
		customer_nameLabel.setToolTipText("Tên Khách hàng");
		customer_nameLabel.setBounds(19, 43, 129, 16);
		panel.add(customer_nameLabel);
		
		JLabel phoneNo = new JLabel("Số Điện thoại");
		phoneNo.setToolTipText("DT");
		phoneNo.setBounds(171, 2, 130, 16);
		panel.add(phoneNo);
		
		JLabel productNameLabel = new JLabel("Tên Sản phẩm");
		productNameLabel.setToolTipText("Tên Sản phẩm");
		productNameLabel.setBounds(19, 92, 129, 16);
		panel.add(productNameLabel);
		
		JLabel addressLabel = new JLabel("Địa chỉ");
		addressLabel.setToolTipText("Địa chỉ");
		addressLabel.setBounds(326, 0, 293, 16);
		panel.add(addressLabel);
		
		JLabel pro_codeLabel = new JLabel("Mã Sản phẩm");
		pro_codeLabel.setToolTipText("Mã SP");
		pro_codeLabel.setBounds(174, 92, 111, 16);
		panel.add(pro_codeLabel);
		
		JLabel pro_unitLabel = new JLabel("Đơn vị tính");
		pro_unitLabel.setToolTipText("Đơn vị tính");
		pro_unitLabel.setBounds(326, 92, 110, 16);
		panel.add(pro_unitLabel);
		
		JLabel pro_priceLabel = new JLabel("Đơn giá");
		pro_priceLabel.setToolTipText("Giá SP");
		pro_priceLabel.setBounds(491, 92, 119, 16);
		panel.add(pro_priceLabel);
		
		JLabel pro_quantityLabel = new JLabel("Số lượng");
		pro_quantityLabel.setToolTipText("Số lượng");
		pro_quantityLabel.setBounds(647, 91, 61, 16);
		panel.add(pro_quantityLabel);
		
		JLabel invoiceDateLabel = new JLabel("Ngày hóa đơn");
		invoiceDateLabel.setToolTipText("Ngày nhập ");
		invoiceDateLabel.setBounds(644, 2, 128, 16);
		panel.add(invoiceDateLabel);
		
		JLabel invoice_noLabel = new JLabel("Mã hóa đơn");
		invoice_noLabel.setToolTipText("Mã Hóa đơn");
		invoice_noLabel.setBounds(805, 2, 165, 16);
		panel.add(invoice_noLabel);
		
//		JCheckBox addProductCheckBox = new JCheckBox("Thêm Sản phẩm");
//		addProductCheckBox.addPropertyChangeListener(new PropertyChangeListener() {
//			public void propertyChange(PropertyChangeEvent evt) {
//			}
//		});
//		addProductCheckBox.setBounds(805, 63, 143, 23);
//		panel.add(addProductCheckBox);
		
		
		
		JLayeredPane invoiceScrolllPane = new JLayeredPane();
		invoiceScrolllPane.setBounds(16, 163, 989, 218);
		contentPane.add(invoiceScrolllPane);
		invoiceScrolllPane.setLayout(new CardLayout(0, 0));
		//table setting
		JScrollPane invoiceScrollPane_1 = new JScrollPane();
		invoiceScrolllPane.add(invoiceScrollPane_1, "name_209777217619627");
		invoiceScrollPane_1.setViewportView(table_sale);
		
		table_sale.setModel(model_sale);
		
		Object column_names[] = {"Số thứ tư", "Mã Sản phẩm", "Tên Sản phẩn", "Đơn vị tính", "Số lượng", "Đơn giá", "Thành tiền"};
		model_sale.setColumnIdentifiers(column_names);
		tableColumnSize();
		
		JLabel total_priceLabel = new JLabel("Tổng tiền hàng");
		total_priceLabel.setBounds(704, 412, 131, 16);
		contentPane.add(total_priceLabel);
		
		JLabel taxLabel = new JLabel("Thuế GTGT - 10%");
		taxLabel.setToolTipText("Thuế GTGT");
		taxLabel.setBounds(704, 443, 131, 16);
		contentPane.add(taxLabel);
		
		JLabel total_amountLabel = new JLabel("Tổng Thanh toán");
		total_amountLabel.setToolTipText("Tổng Thanh toán");
		total_amountLabel.setBounds(704, 474, 131, 16);
		contentPane.add(total_amountLabel);
		
		total_priceTextField = new JTextField();
		total_priceTextField.setBounds(836, 404, 169, 26);
		contentPane.add(total_priceTextField);
		total_priceTextField.setColumns(10);
		
		taxTextField = new JTextField();
		taxTextField.setColumns(10);
		taxTextField.setBounds(836, 433, 169, 26);
		contentPane.add(taxTextField);
		
		total_amountTextField = new JTextField();
		total_amountTextField.setColumns(10);
		total_amountTextField.setBounds(836, 464, 169, 26);
		contentPane.add(total_amountTextField);
		
		JButton printInvoiceButton = new JButton("In Hóa đơn");
		printInvoiceButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Printing receipt:");
				printInvoice();
			}
		});
		printInvoiceButton.setToolTipText("In");
		printInvoiceButton.setBounds(846, 500, 148, 68);
		contentPane.add(printInvoiceButton);
		
		JButton addNewProductButton = new JButton("Thêm sản phẩm");
		addNewProductButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				productName.setSelectedItem("");
				pro_codeTextField.setText("");
				pro_unitTextField.setText("");
				pro_priceTextField.setText("");
				pro_quantityTextField.setText("");
				total_costprice.setText("");
			}
		});
		addNewProductButton.setBackground(new Color(244, 241, 243));
		addNewProductButton.setToolTipText("In");
		addNewProductButton.setBounds(370, 417, 148, 68);
		contentPane.add(addNewProductButton);
		
		total_amountTextField = new JTextField();
		total_amountTextField.setColumns(10);
		total_amountTextField.setBounds(836, 464, 169, 26);
		contentPane.add(total_amountTextField);
		
		customer_code = new JTextField();
		customer_code.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				getDetailedWithCustomerCode();
			}
		});
		customer_code.setColumns(10);
		customer_code.setBounds(18, 17, 130, 26);
		panel.add(customer_code);
		
		lblMKhchHng = new JLabel("Mã khách hàng");
		lblMKhchHng.setToolTipText("DT");
		lblMKhchHng.setBounds(18, 2, 130, 16);
		panel.add(lblMKhchHng);
		
		total_costprice = new JTextField();
		total_costprice.setBounds(805, 110, 165, 26);
		panel.add(total_costprice);
		total_costprice.setColumns(10);
		
		pro_totalprice = new JLabel("Thành tiền");
		pro_totalprice.setToolTipText("Số lượng");
		pro_totalprice.setBounds(805, 92, 165, 16);
		panel.add(pro_totalprice);
		
		JButton createNewButtonButton = new JButton("Tạo Hóa đơn mới");
		createNewButtonButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				productName.setSelectedItem("");
				pro_codeTextField.setText("");
				pro_unitTextField.setText("");
				pro_priceTextField.setText("");
				pro_quantityTextField.setText("");
				total_costprice.setText("");
				customer_code.setText("");
				phone_noTextField.setText("");
				customer_nameComboBox.setSelectedItem("");
				addressTextField.setText("");
				invoiceDateTextField.setText("");
				invoice_noTextField.setText("");
				total_priceTextField.setText("");
				total_amountTextField.setText("");
				taxTextField.setText("");
				
				model_sale.setNumRows(0);
			}
		});
		
		createNewButtonButton.setToolTipText("In");
		createNewButtonButton.setBackground(new Color(244, 241, 243));
		createNewButtonButton.setBounds(133, 412, 148, 68);
		contentPane.add(createNewButtonButton);
		
		productDropdown();
		getCusDropDown();

	}
}