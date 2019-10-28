package bucketlist;

import java.util.*;
import java.io.*;

public class Main {

	public static void main(String[] args) {
		Main my = new Main();
		my.mymain();
	}

	Scanner keyin = new Scanner(System.in);
	ArrayList<Museum> museumList = new ArrayList<>();
	ArrayList<Category> categoryList = new ArrayList<>();

	void mymain() {
		Scanner scan = openFile("c:/MUSEUM_BUCKET/data/Museum_enter.txt");
		Scanner scan_category = openFile("c:/MUSEUM_BUCKET/data/Category.txt");
		Museum museum = null;
		Category cate = null;

		while (scan_category.hasNext()) {
			cate = new Category();
			cate.read(scan_category);
			categoryList.add(cate);

		}
		scan_category.close();

		while (scan.hasNext()) {
			museum = new Museum();
			museum.read(scan);
			museum.category = getCategory(museum.code);
			museumList.add(museum);
		}
		scan.close();

		User user = new User();
		Bucket bucket = new Bucket();
		Museum userTaste = new Museum();

		while (true) {
			int menu = 1;
			System.out.printf(
			  "%n===================%n" 
			+ " Museum BucketList %n" 
			+ "===================%n%n"
			+ "(1) 로그인%n" 
			+ "(2) 회원가입%n%n" 
			+ "(0) 종료%n" 
			+ "===================%n%n");
			System.out.print(">> ");
			menu = keyin.nextInt();
			if (menu == 0) {
				break;
			}

			if (menu == 1) {
				try {
					if (user.login(keyin)) {
						while (true) {
							int N = 0;
							System.out.println(
							  "(1) 검색\n" 
							+ "(2) 버킷리스트\n" 
							+ "(3) 통계\n\n" 
							+ "(0) 로그아웃\n");
							System.out.print(">> ");
							N = keyin.nextInt();
							if (N == 0)
								break;

							switch (N) {
							case 1:
								int n2 = 0;
								System.out.printf(
								  "(1) 검색%n" 
								+ "(2) 추천 검색%n" 
							    + "(3) 전체 출력%n%n" 
								+ "(0) 뒤로가기%n%n");
								System.out.print(">> ");
								n2 = keyin.nextInt();
								if (n2 == 0)
									break;

								if (n2 == 1) {
									userTaste.kwdSearch(this, user, keyin);
									break;
								}

								if (n2 == 2) {
									userTaste.localCheck(keyin);
									userTaste.styleCheck(keyin);
									userTaste.printTaste(this);
									break;
								}

								if (n2 == 3) {
									userTaste.printTotal(this);
									break;
								}
							case 2:
								int n3 = 0;
								System.out.printf(
								  "(1) 버킷리스트 출력%n" 
								+ "(2) 데이터 추가%n" 
								+ "(3) 데이터 삭제%n" 
								+ "(4) 방문 체크%n"
								+ "(5) 방문 삭제%n%n" 
								+ "(0) 메뉴%n%n");
								System.out.print(">> ");
								n3 = keyin.nextInt();
								if (n3 == 0)
									break;

								if (n3 == 1) {
									try {
										bucket.printBucket(this, user);
										break;
									} catch (FileNotFoundException e) {
										e.printStackTrace();
									}
								}

								if (n3 == 2) {
									try {
										bucket.addPlace(keyin, this, user);
										user.refresh();
										break;
									} catch (IOException e) {
										e.printStackTrace();
									}
								}

								if (n3 == 3) {
									try {
										bucket.removePlace(this, user, keyin);
										user.refresh();
										break;
									} catch (IOException e) {
										e.printStackTrace();
									}
								}

								if (n3 == 4) {
									try {
										bucket.visitCheck(keyin, this, user);
										break;
									} catch (IOException e) {
										e.printStackTrace();
									}
								}

								if (n3 == 5) {
									try {
										bucket.visitRemove(this, keyin, user);
										break;
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							case 3:
								int n4 = 0;
								System.out.printf(
								  "(1) 사용자 통계 %n" 
								+ "(2) 뮤지엄 랭킹%n" 
								+ "(3) 뮤지엄 추천%n%n" 
								+ "(0) 메뉴%n%n");
								System.out.print(">> ");
								n4 = keyin.nextInt();
								if (n4 == 0)
									break;

								if (n4 == 1) {
									bucket.kwdStatistics(this, museum, user);
									bucket.userStatis(this, user);
									break;
								}

								if (n4 == 3) {
									bucket.recomend(this, museum, user);
									break;
								}

								if (n4 == 2) {
									museum.rankMuseum(this, user);
									break;
								}
							}
						}

					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			if (menu == 2) {
				try {
					user.signUp(keyin);
				} catch (IOException e) {
					System.out.println("에러 발생");
				}
			}
		}
	}

	Category getCategory(String code) {
		for (Category a : categoryList) {
			if (a.code.equals(code)) {
				return a;
			}
		}
		return null;
	}

	Scanner openFile(String file) {
		File f = new File(file);
		Scanner scan = null;

		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("파일이 존재하지 않습니다.");
		}
		return scan;
	}

}
