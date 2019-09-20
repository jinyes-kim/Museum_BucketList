package bucketlist;

import java.io.IOException;
import java.util.*;

public class Museum {
	String name;
	String national;
	String homepage;
	String address;
	String code;
	Category category;
	int count=0;
	
	ArrayList<String> styleList = new ArrayList<>();
	ArrayList<String> localList = new ArrayList<>();
	
	void rankMuseum(Main main, User user) {
		// 전체 사용자들이 버킷리스트에 담은 뮤지엄 통계
		ArrayList<String> rankMuseum = new ArrayList<>();
		ArrayList<Integer> ranks = new ArrayList<>();
		ArrayList<Integer> rankss = new ArrayList<>();
		HashMap<String, Integer> nMuseum = new HashMap<>();
		
		Scanner scan = main.openFile("c:/MUSEUM_BUCKET/data/ALLDATAMUSEUM.txt");
		String input = null;


		while(scan.hasNext()) {
			scan.next();
			input = scan.next();
			rankMuseum.add(input);
			nMuseum.put(input, 0);
		}
		
		// 1단계 중복 뮤지엄 제거
		for (String a : rankMuseum) {
			if (nMuseum.containsKey(a)) {
				nMuseum.put(a, nMuseum.get(a)+1);
			}
		}
		//2 단계 카운팅만 빼와서 내림차순 정렬하기
		for (int a : nMuseum.values()) {
			ranks.add(a);
		}
		
		Collections.sort(ranks, Collections.reverseOrder());
		
		// 3단계 카운팅 중복 제거
		for(int a : ranks) {
			if(!rankss.contains(a)) {
				rankss.add(a);
			}
		}
		
		// 4단계 출력하기
		int k = 1;
		for (int a : rankss) {
			for (String b : nMuseum.keySet()) {
				if (nMuseum.get(b) == a) {
					System.out.printf("[%2s]위 ", k);
					findMuseum(main, b);
					k++;
				}
			}
		}
		System.out.println();
		
	}

	void findMuseum(Main main, String kwd) {
		for (Museum a : main.museumList) {
			if(a.name.equals(kwd)) {
				a.print();
				break;
			}
		}
	}
	
	Museum returnMuseum(Main main, String kwd) {
		for (Museum a : main.museumList) {
			if (a.name.equals(kwd)) {
				return a;
			}
		}
		return null;
	}
	
	void kwdSearch(Main main, User user, Scanner scan) {
		boolean check = false;
		String kwd = null;
		System.out.println(
				"검색 예시: 국립, 사립, 서울시, 미술관, 테마파크...");
		System.out.print(">> ");
		kwd = scan.next();
		
		try {
			user.bw2.write(kwd + " ");
			user.openMasterData();
			user.allData.write(kwd);
			user.allData.newLine();
			user.refreshSerach();
			user.refreshMaster();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (!check) {
			for (Museum a : main.museumList) {
				if (a.address.contains(kwd.substring(0, 2))) {
					a.print();
					check = true;
				}
			}
			System.out.println();
		}
		
		if (kwd.equals("국립") || kwd.equals("사립")) {
			for (Museum a : main.museumList) {
				if (a.national.equals(kwd)) {
					a.print();
					check = true;
				}
			}
			System.out.println();
		}
		
		if (kwd.endsWith("시")) {
			for (Museum a : main.museumList) {
				if (a.address.contains(kwd)){
					a.print();
					check = true;
				}
			}
			System.out.println();
		}
		
		if (kwd.endsWith("관") || kwd.equals("테마파크") || 
				kwd.equals("갤러리")){
			
			for (Museum a : main.museumList) {
				if (a.category.style.equals(kwd)) {
					a.print();
					check = true;
				}
			}
			System.out.println();
		}
		
		if(!check) {
			for (Museum a : main.museumList) {
				if (a.name.contains(kwd)) {
					a.print();
					check = true;
				}
			}
			System.out.println();
		}
		
		if (!check) {
			System.out.println("검색 결과가 없습니다.");
		}
	}

	void localCheck(Scanner scan) {
		String input = null;
		String userInput = null;
		
		System.out.printf(""
				+ "원하는 지역들을 입력하세요%n"
				+ "예시: 서울시, 수원시, 부산시%n%n"
				+ "종료버튼: 0%n%n");
		
		while(true) {
			boolean check = false;
			
			System.out.print(">> ");
			input = scan.next();
			userInput = input.substring(0, input.length()-1);
			if (input.equals("0")) {
				break;
			}
			
			if (!input.endsWith("시")) {
				System.out.printf("시 단위로 입력해주세요.%n");
				continue;
			}
			
			for (String a : localList) {
				if (a.equals(userInput)){
					System.out.printf("이미 입력한 지역입니다.%n");
					check = true;
					break;
				}
			}
			
			if(!check) {
				localList.add(userInput);
			}
		}
	}
	void styleCheck(Scanner scan) {
		String input = null;
		
		System.out.printf("키워드 번호를 입력하세요.%n");
		System.out.printf(
				"(1) 박물관%n(2) 미술관%n(3) 갤러리%n(4) 기념관%n"
				+"(5) 역사관%n(6) 과학관%n(7) 테마파크%n"
				+ "(8) 문화관%n(0) 종료%n%n");
		
		while(true) {
			boolean check = false;
			
			System.out.print(">> ");
			input = scan.next();
			if(input.equals("0")) {
				break;
			}
			
			for (String a : styleList) {
				if (a.equals(input)){
					System.out.printf("중복된 요소입니다.%n");
					check = true;
				}
			}
			
			if(!check) {
				styleList.add(input);
				check = true;
			}
		}
	}
	
	void printTaste(Main main) {
		boolean check = false;
		ArrayList<Museum> filter1 = new ArrayList<>();
		
		for (String a : localList) {
			for (Museum b : main.museumList) {
				if (b.address.contains(a)) {
					filter1.add(b);
				}
			}
		}
		
		for (String a : styleList) {
			for (Museum b : filter1) {
				if (a.equals(b.code)) {
					b.print();
					check = true;
				}
			}
		}
		System.out.println();
		if(!check) {
			System.out.printf("해당 데이터가 존재하지 않습니다.%n%n");
		}
	}
	
	void read(Scanner scan) {
		name = scan.next();
		national = scan.next();
		homepage = scan.next();
		code = scan.next();
		address = scan.nextLine();		
	}
	
	void printTotal(Main main) {
		for(Museum a : main.museumList) {
			a.print();
		}
	}
	
	// 국립, 이름, 스타일, 주소, 홈페이지
	void print() {
		System.out.println(
				String.format("[%2s] [%4s] %25s   %-40s\t%-50s", 
				 national, category.style, name, address, homepage));
	}
	
	void printLong(Main main, User user, Bucket bucket) {
		if(bucket.checkBucket(main, user, name)) {
			System.out.printf("[%s] ", "O");
		}
		else {
			System.out.printf("[%s] ", "X");
		}
		System.out.println(
				String.format("[%2s] [%4s] %25s   %-40s\t%-50s", 
				 national, category.style, name, address, homepage));
	}
}
