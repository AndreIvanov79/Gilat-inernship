package students.examples.gg.encrypt_decrypt;


import java.io.BufferedReader;
import java.io.InputStreamReader;

public class EncDecMain {
	public static void main(String[] args) {
		
		EncrypDecrypt encDec = new EncrypDecrypt();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));) {
			
			System.out.println("Enter the code: " + "\n");
			
			while ((reader.readLine()) != null) {
				System.out.println("Enter the code: " + "\n");
				
				int i = Integer.parseInt(reader.readLine());
				
				if (i == 1) {
					encDec.generateAndSaveKey();
					
				}

				if (i == 2) {
					System.out.println("Enter your message: " + "\n");

					String message = reader.readLine();
					encDec.encryptMessageAndSave(message);
					System.out.println("Enter the code: " + "\n");
					i = Integer.parseInt(reader.readLine());
				}

				if (i == 3) {
					encDec.decryptAndPrint();
					System.out.println("Enter the code: " + "\n");
					i = Integer.parseInt(reader.readLine());
				}
				if (i== 0) break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
