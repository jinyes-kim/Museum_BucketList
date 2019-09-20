package bucketlist;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

//지역으로 추천하는 거 추가
//버킷리스트 통계에서 국립/사립/공립 비율 추가

public class Bucket {
	ArrayList<String> checkList = new ArrayList<>();
	ArrayList<String> newBuckets = new ArrayList<>();
	ArrayList<Museum> bucketList = new ArrayList<>();
	ArrayList<String> statis = new ArrayList<>();
	
	String[] kwdList = { "박물관", "미술관", "갤러리",
			"기념관", "역사관", "과학관", "테마파크", "문화관" };
	

	void recomend(Main main, Museum museum, User user) {
		Scanner scan = main.openFile("c:/MUSEUM_BUCKET/data/" + user.id + "_search.txt");
		ArrayList<String> userKwd = new ArrayList<>();
		ArrayList<Integer> ranks = new ArrayList<>();
		HashMap<String, Integer> kwdRank = new HashMap<>();
		String source = null;
		
		while(scan.hasNext()) {
			source = scan.next();
			userKwd.add(source);
			kwdRank.put(source, 0);
		}
		scan.close();
		
		for (String a : userKwd) {
			if (kwdRank.containsKey(a)) {
				kwdRank.put(a, kwdRank.get(a)+1);
			}
		}
		
		for (int a : kwdRank.values()) {
			ranks.add(a);
		}
		
		Collections.sort(ranks, Collections.reverseOrder());
		
		String f = null;

		for (String b : kwdRank.keySet()) {
			if(kwdRank.get(b) == ranks.get(0)) {
				f = b;
				break;
				}	
		}
				
		// 변수 f는 모스트 키워드
		System.out.println("===========================");
		System.out.printf(
				"\n  %s님은 %s 키워드를 추천합니다.\n\n", user.id, f);
		System.out.println("===========================");
		System.out.println("   <추천 뮤지엄 리스트>\n");
		ArrayList<String> rankMuseum = new ArrayList<>();
		ArrayList<Museum> treat = new ArrayList<>();
		ArrayList<Museum> resultTreat = new ArrayList<>();
		ArrayList<String> checkList = new ArrayList<>();

		Scanner keyin = main.openFile("c:/MUSEUM_BUCKET/data/ALLDATAMUSEUM.txt");
		Scanner keyin2 = main.openFile("c:/MUSEUM_BUCKET/data/" + user.id + ".txt");
		String input = null;
		Museum m = null;
		
		while(keyin.hasNext()) {
			keyin.next();
			input = keyin.next();
			rankMuseum.add(input);
		}
		keyin.close();
		
		while(keyin2.hasNext()) {
			keyin2.next();
			input = keyin2.next();
			checkList.add(input);
		}
		keyin2.close();
		
		for (String a : rankMuseum) {
			m = museum.returnMuseum(main, a);
			// 해당 뮤지엄의 객체를 반환
			
			if(m.national.equals(f)) {
				treat.add(m);
			}
			
			if(m.name.contains(f)) {
				treat.add(m);
			}
			
			if(m.category.style.equals(f)) {
				treat.add(m);
			}
		}
		
		for (Museum a : treat) {
			if(!resultTreat.contains(a) && !checkList.contains(a.name)) {
				resultTreat.add(a);
			}
		}
		
		for (Museum a : resultTreat) {
			a.print();
		}
		
		System.out.println("\n추천 목록은 사용자 데이터를 기반으로 합니다.\n");
	}
	
	void userStatis(Main main, User user) throws IOException {
		Scanner scan = main.openFile("c:/MUSEUM_BUCKET/data/" + user.id + "_search.txt");
		ArrayList<String> userKwd = new ArrayList<>();
		ArrayList<Integer> rankCount = new ArrayList<>();
		ArrayList<Integer> rankCount2 = new ArrayList<>();
		HashMap<String, Integer> kwdRank = new HashMap<>();
		String source = null;
		
		while(scan.hasNext()) {
			source = scan.next();
			userKwd.add(source);
			kwdRank.put(source, 0);
			//해쉬맵은 키 중복이 안된다.
		}
		scan.close();
		
		for (String a : userKwd) {
			if(kwdRank.containsKey(a)){
				kwdRank.put(a, kwdRank.get(a)+1);
			}
		}
		
		for (int a : kwdRank.values()) {
			rankCount.add(a);
		}
		
		Collections.sort(rankCount, Collections.reverseOrder());
		
		// 중복 숫자 제거
		for (int a : rankCount) {
			if (!rankCount2.contains(a)) {
				rankCount2.add(a);
			}
		}
		
		System.out.println("====================");
		System.out.println("<유저 검색어 통계>");
		System.out.println("====================\n");
		int k = 1;
		for (int a : rankCount2) {
			if (k > 6) break;
			for (String b : kwdRank.keySet()) {
				if (kwdRank.get(b) == a) {
					System.out.printf("[%2s위] ", k);
					System.out.println(b);
					k++;
				}
			}
		}
		
		System.out.println("\n *유저 검색 데이터를 기반으로 합니다.");
	}

	void kwdStatistics(Main main, Museum museum, User user) {
		Scanner scan = main.openFile("c:/MUSEUM_BUCKET/data/" + user.id + ".txt");
		ArrayList<String> style = new ArrayList<>();
		ArrayList<Integer> ranks = new ArrayList<>();
		ArrayList<Integer> ranks2 = new ArrayList<>();
		ArrayList<String> local = new ArrayList<>();
		ArrayList<Integer> localRank = new ArrayList<>();
		ArrayList<Integer> localRank2 = new ArrayList<>();
		HashMap<String, Integer> localMap = new HashMap<>();
		HashMap<String, Integer> result = new HashMap<>();
		String input = null;
		String input2 = null;
		
		while (scan.hasNext()) {
			input = scan.next();
			input2 = scan.next();
			style.add(input);
			local.add(input2);
			result.put(input, 0);
		}
		scan.close();
		System.out.println("====================");
		System.out.printf("<버킷리스트 통계>%n");
		System.out.println("====================");
		
		Museum m = null;
		for (String a : local) {
			m = museum.returnMuseum(main, a);
			if (!localMap.containsKey(m.address.substring(0,3))) {
				localMap.put(m.address.substring(0, 3), 0);
			}
		}
		
		for (String a : local) {
			m = museum.returnMuseum(main, a);
			if (localMap.containsKey(m.address.substring(0,3))) {
				localMap.put(m.address.substring(0, 3), 
						localMap.get(m.address.substring(0, 3))+1);
			}
		}
		
		for (int a : localMap.values()) {
			localRank.add(a);
		}
		
		Collections.sort(localRank, Collections.reverseOrder());
		
		for (int a : localRank) {
			if(!localRank2.contains(a)) {
				localRank2.add(a);
			}
		}
		
		System.out.println("지역 통계");
		int k2 = 1;
		for (int a : localRank2) {
			for (String b : localMap.keySet()) {
				if (localMap.get(b) == a) {
					System.out.printf("[%s위] ", k2);
					System.out.printf(
							"%s %.2f%%%n", 
							b, (double) a / style.size() * 100 );
					k2++;
				}
			}
		}
		
		
		for(String a : style) {
			if (result.containsKey(a)) {
				result.put(a, result.get(a)+1);
			}
		}
		
		for (int a : result.values()) {
			ranks.add(a);
		}
		
		Collections.sort(ranks, Collections.reverseOrder());
		
		for (int a : ranks) {
			if (!ranks2.contains(a)) {
				ranks2.add(a);
			}
		}
		
		System.out.println("\n키워드 통계");
		int k = 1;
		for (int a : ranks2) {
			for (String b : result.keySet()) {
				if (result.get(b) == a) {
					System.out.printf("[%s위]", k);
					System.out.printf(
							" %s %.2f%%\n", 
							b, (double) a / style.size() * 100);
					k++;
				}
			}
		}
		
		HashMap<String, Integer> nation = new HashMap<>();
		ArrayList<Integer> nationRank = new ArrayList<>();
		ArrayList<Integer> nationRank2 = new ArrayList<>();
		System.out.println("\n국공립 통계");
		
		for (String a : local) {
			String res = museum.returnMuseum(main, a).national;
			nation.put(res, 0);	
		}
		
		for (String a : local) {
			String res = museum.returnMuseum(main, a).national;
			if (nation.containsKey(res)) {
				nation.put(res, nation.get(res)+1);
			}
		}
		
		for (int a : nation.values()) {
			nationRank.add(a);
		}
		
		Collections.sort(nationRank, Collections.reverseOrder());
		
		for (int a : nationRank) {
			if (!nationRank2.contains(a)) {
				nationRank2.add(a);
			}
		}
		
		int k3 = 1;
		for (int a : nationRank2) {
			for (String b : nation.keySet()) {
				if (nation.get(b) == a) {
					System.out.printf("[%d위] %s %.2f%%%n",
							k3, b, (double) a/local.size() * 100);
					k3++;
				}
			}
		}
		
		System.out.println();
	}
	
	boolean checkBucket(Main main, User user, String kwd) {
		ArrayList<String> buckets = new ArrayList<>();
		Scanner scan = main.openFile("c:/MUSEUM_BUCKET/data/" + user.id + "___check.txt");
		String s = null;
		
		while(scan.hasNext()) {
			s = scan.next();
			buckets.add(s);
		}
		
		for (String a : buckets) {
			if(a.equals(kwd)) {
				return true;
			}
		}
		
		return false;	
	}

	void visitCheck(Scanner scan, Main main, User user) throws IOException {
		boolean check = false;
		boolean check2 = false;
		String ch = null;
		String muse = null;
		String muse2 = null;
		ArrayList<String> bucketList = new ArrayList<>();
		ArrayList<String> bucketList2 = new ArrayList<>();
		Scanner scan2 = main.openFile("c:/MUSEUM_BUCKET/data/" + user.id +".txt");
		Scanner scan3 = main.openFile("c:/MUSEUM_BUCKET/data/" + user.id +"___check.txt");
		
		while(scan2.hasNext()) {
			scan2.next();
			muse = scan2.next();
			bucketList.add(muse);
		}
		scan2.close();
		
		while(scan3.hasNext()) {
			muse2 = scan3.next();
			bucketList2.add(muse2);
		}
		scan3.close();
		
		System.out.print("방문한 곳을 입력해주세요.\n\n종료 버튼: 0\n");
		while(true) {	
			System.out.print(">> ");
			ch = scan.next();
			if (ch.equals("0")) {
				break;
			}
			
			for(String a : bucketList2) {
				if(a.equals(ch)) {
					System.out.printf(
							"%s는 이미 버킷리스트에 존재합니다.\n", a);
					check = true;
				}
			}
			
			if(!check) {
				for (String a : bucketList) {
					if(a.equals(ch)) {
						user.openCheck();
						user.che.write(ch);
						user.che.newLine();
						user.refreshCheck();
						System.out.printf("'%s' 방문체크 완료 \n\n", a);
						check2 = true;
						break;
					}
				}
			}
			if (!check2) {
				System.out.printf(
						"'%s'는 버킷리스트에 존재하지 않습니다.\n", ch);
				break;
			}
		}
		
	}

	void visitRemove(Main main, Scanner scan, User user) throws IOException {
		boolean check = false;
		String input = null;
		String s = null;
		Scanner scan2 = main.openFile("c:/MUSEUM_BUCKET/data/" + user.id +"___check.txt");
		ArrayList<String> buckets = new ArrayList<>();
		
		while(scan2.hasNext()) {
			s = scan2.next();
			buckets.add(s);
		}
		scan2.close();
		
		System.out.println("체크 해제할 곳을 입력해주세요.\n");
		
		while (true) {
			check = false;
			System.out.print(">> ");
			input = scan.next();
			if (input.equals("0")) {
				break;

			}
			
			for(String a : buckets) {
				if (a.equals(input)) {
					buckets.remove(a);
					check = true;
					break;
				}
			}
			
			if(check) {
				for(String a : buckets) {
					user.overWriteCheck();
					user.che.write(a);
					user.che.newLine();
					user.refreshCheck();
				}
				System.out.printf("'%s' 해제 완료\n", input );
			}

			if (!check) {
				System.out.println(
						input + "은(는) 방문 체크 리스트에 존재하지 않습니다.\n");
			}

		}
	}

	void printBucket(Main main, User user) throws FileNotFoundException {
		boolean check = false;
		String name = null;
		
		ArrayList<String> buckets = new ArrayList<>();
		Scanner scan = main.openFile("c:/MUSEUM_BUCKET/data/" + user.id + ".txt");
		try {
			user.openCheck();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (scan.hasNext()) {
			scan.next();
			name = scan.next();
			buckets.add(name);
		}	
		scan.close();
		
		int k = 0;
		for (String a : buckets) {
			for (Museum b : main.museumList) {
				if (a.equals(b.name)){
					b.printLong(main, user, this);
					k++;
					check = true;
				}
			}
		}
		System.out.println();
		System.out.printf("  %d건의 목록이 존재합니다.\n\n", k);
		
		if (!check) {
			System.out.println("버킷리스트에 데이터가 없습니다.\n");
		}
	}

	void removePlace(Main main, User user, Scanner scan) throws IOException {
		boolean check = false;
		String input = null;
		String input2 = null;
		ArrayList<String> temp = new ArrayList<>();
		
		System.out.printf("제거할 장소를 입력해주세요.%n종료 버튼: 0%n");
		
		while (true) {
			check = false;
			Scanner scan2 = main.openFile("c:/MUSEUM_BUCKET/data/" + user.id + ".txt");
			
			while(scan2.hasNext()) {
				scan2.next();
				input2 = scan2.next();
				temp.add(input2);
			}
			scan2.close();
			
			System.out.print(">> ");
			input = scan.next();
			if (input.equals("0")) {
				break;
			}
			
			for (String a : temp) {
				if (a.equals(input)){
					temp.remove(a);
					check = true;
					break;
				}
			}
			
			if(check) {
				try {
					user.overWrite();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				for (String a : temp) {
					for (Museum b : main.museumList) {
						if (a.equals(b.name)) {
							user.bw.write(b.category.style + " " 
							+ b.name);
							user.bw.newLine();
						}
					}
				}
				System.out.println("삭제 완료");
				user.refresh();
			}
			
			if (!check) {
				System.out.printf("'%s'이 버킷리스트에 존재하지 않습니다."
						+ "%n", input);
			}
		}
	}

	void addPlace(Scanner scan, Main main, User user) throws IOException {
		String input = null;
		String input2 = null;
		System.out.printf("추가할 장소를 입력해주세요.%n종료버튼: 0%n%n");
		
		while (true) {
			Scanner scan2 = main.openFile("c:/MUSEUM_BUCKET/data/" + user.id + ".txt");
			ArrayList<String> temp = new ArrayList<>();
			
			while(scan2.hasNext()) {
				scan2.next();
				input2 = scan2.next();
				temp.add(input2);
			}
			scan2.close();
			
			boolean check = false;
			System.out.print(">> ");
			input = scan.next();
			if (input.equals("0")) {
				break;
			}
			
			// 버킷리스트에 있는지 체크
			for (String a : temp) {
				if (input.equals(a)) {
					System.out.printf(
							"이미 버킷리스트에 %s이 존재합니다.%n", a);
					check = true;
					break;
				}
			}
			
			if (!check) {
				for (Museum a : main.museumList) {
					if (a.name.equals(input)) {
						System.out.printf(
								"'%s' 버킷리스트에 추가 되었습니다.%n",
								input);
						user.bw.write(a.category.style + " ");
						user.bw.write(a.name);
						user.bw.newLine();
						user.refresh();
						user.openAllData();
						user.bw_all.write(a.category.style + " ");
						user.bw_all.write(a.name);
						user.bw_all.newLine();
						user.refreshAllData();
						check = true;
						break;

					}
				}
				if (!check) {
					System.out.printf("%s은(는) 존재하지 않습니다.%n", input);
					break;
				}
			}
			
		}
	}
}
