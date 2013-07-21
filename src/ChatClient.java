import java.awt.*;
import java.awt.event.*;


public class ChatClient extends Frame {
	
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	
	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}
	
	public void launchFrame(){
		setBounds(50, 50, 500, 500);
		add(tfTxt, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();
			
		this.addWindowListener(new WindowAdapter(){
			
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
			
		});
		
		tfTxt.addActionListener(new TFListener());
		setVisible(true);
	}
	
	private class TFListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String strInput = tfTxt.getText().trim();
			taContent.setText(strInput);
			tfTxt.setText("");
			
		}
		
	}
}