package bucketlist;

// 유저 DB 파일 User.txt
// 유저 ID로 유저 데이터 생성
import java.util.*;
import java.io.*;

public class User {

	static Main main;
	String id;
	String pw;
	String[] idpw = null;
	boolean check = false;
	BufferedReader br = null;
	BufferedWriter bw = null; // bw.write 메서드로 쓰고 닫아야 함
	BufferedWriter bw2 = null;
	BufferedWriter bw_all = null;
	BufferedWriter allData = null;
	BufferedWriter che = null;

	void overWrite() throws IOException {
		bw = new BufferedWriter(new FileWriter(
				"c:/MUSEUM_BUCKET/data/" + id + ".txt"));
	}
	
	void overWriteCheck() throws IOException {
		che = new BufferedWriter(new FileWriter(
				"c:/MUSEUM_BUCKET/data/" + id + "___check.txt"));
	}
	
	void refreshCheck() throws IOException {
		che.close();
		openCheck();
	}
	
	void openCheck() throws IOException {
		che = new BufferedWriter(new FileWriter(
				"c:/MUSEUM_BUCKET/data/" + id + "___check.txt", true));
		
	}
	
	void refreshMaster() throws IOException {
		allData.close();
		openMasterData();
	}
	
	void refreshSerach() throws IOException {
		bw2.close();
		openSearchData();
	}
	
	void refresh() throws IOException {
		bw.close();
		openData();
	}
	
	void openMasterData() throws IOException {
		allData = new BufferedWriter(new FileWriter(
				"c:/MUSEUM_BUCKET/data/ALL___1234All.txt", true));
	}
	void refreshAllData() throws IOException {
		bw_all.close();
		openAllData();
	}
	void openAllData() throws IOException {
		bw_all = new BufferedWriter(new FileWriter(
				"c:/MUSEUM_BUCKET/data/ALLDATAMUSEUM.txt", true));
	}

	void openData() throws IOException {
		bw = new BufferedWriter(new FileWriter(
				"c:/MUSEUM_BUCKET/data/" + id + ".txt", true));
	}
	void openSearchData() throws IOException {
		bw2 = new BufferedWriter(new FileWriter(
				"c:/MUSEUM_BUCKET/data/" + id + "_search.txt", true));
	}
	void readData() throws FileNotFoundException {
		br = new BufferedReader(new FileReader(
				"c:/MUSEUM_BUCKET/data/" + id + ".txt"));
	}
	
	void logout() throws IOException {
		bw.close();
		System.out.println(id + "로그아웃 완료\n");
	}

	boolean login(Scanner scan) throws IOException {
		check = false;
		System.out.print("ID >> ");
		id = scan.next();
		System.out.print("PW >> ");
		pw = scan.next();

		br = new BufferedReader(
				new FileReader("c:/MUSEUM_BUCKET/data/User.txt"));

		while (true) {
			idpw = new String[2];
			String line = br.readLine();
			if (line == null) {
				break;
			}

			idpw = line.split(" ");
			if (id.equals(idpw[0]) && pw.equals(idpw[1])) {
				System.out.println("===================");
				System.out.printf("  %s님 환영합니다.%n", id);
				System.out.println("===================");
				openData();
				openSearchData();
				openMasterData();
				openAllData();
				openCheck();
				check = true;
				return true;
			}
		
		}
		
		if (!check) {
			System.out.println(
					"\n존재하지 않는 회원이거나 비밀번호가 일치하지 않습니다\n");
			return false;
		}
		return false;
		

	}

	void signUp(Scanner scan) throws IOException {
		boolean check2 = true;
		System.out.println("회원가입을 진행합니다.\n"
				+ "*비밀번호는 5자 이상으로 설정해주세요.\n");
		System.out.print("ID >> ");
		id = scan.next();
		System.out.print("PW >> ");
		pw = scan.next();
		if (pw.length() < 4) {
			System.out.println("비밀번호는 5자 이상으로 설정하세요.\n");
			check2 = false;
		}

		if (!check2) {
			System.out.println("회원가입을 다시 진행해주세요. \n");
		}
		
		if (check2) {
			br = new BufferedReader(
					new FileReader("c:/MUSEUM_BUCKET/data/User.txt"));
			
			while (true) {
				idpw = new String[2];
				check = false;

				while (true) {
					check = false;
					String line = br.readLine();
					if (line == null) {
						break;
					}
					idpw = line.split(" ");
					if (idpw[0].equals(id)) {
						check = true;
						break;
					}
				}
				if (check) {
					System.out.println(
							"\n" + id + "는(은) 이미 사용중인 ID입니다.\n");
					break;
				}

				if (!check) {
					openCheck();
					BufferedWriter fw = new BufferedWriter(
							new FileWriter("c:/MUSEUM_BUCKET/data/User.txt", true));
					fw.newLine();
					fw.write(id + " " + pw);
					System.out.println("회원가입이 완료되었습니다.\n");
					fw.close();
					
					check = true;
					break;
				}
				br.close();
			}
		}
	}
}
