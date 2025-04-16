package bank.schema3;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountManager implements ICustomDefine{

	//키보드 입력을 위한 인스턴스
		static Scanner scan = new Scanner(System.in);
		//계좌정보 저장을 위한 인스턴스배열
		static Account[] accounts = new Account[50];
		//개설된 계좌정보 카운트용 변수
		static int accCnt = 0;
		
	public static void showMenu() {
			System.out.println("1. 계좌개설");
			System.out.println("2. 입금");
			System.out.println("3. 출금");
			System.out.println("4. 전체계좌정보출력");
			System.out.println("5. 프로그램종료");
			
		} 
		
	
	// 계좌개설을 위한 함수
	public static void makeAccount() {
		System.out.println("1. 보통예금계좌  2. 신용신뢰계좌");
		int choice =Integer.parseInt(scan.nextLine());
		
		System.out.print("계좌번호: ");
		String a = scan.nextLine();
		System.out.print("이름: ");
		String n = scan.nextLine();
		System.out.print("잔고: ");
		int b = scan.nextInt();
		System.out.print("이자율%: ");
		int interest = scan.nextInt();
		scan.nextLine();
		
		if (choice == 1) {
			Account ac = new NormalAccount(a, n, b, interest);
			accounts[accCnt++] = ac;
			System.out.println("신규계좌 개설 완료");
		} else {
			String grade = "";
			int creditRate = 0;
			if (interest >= A) {
				creditRate = A;
				grade = "A";
			} else if (interest >= B) {
				creditRate = B;
				grade = "B";
			} else {
				creditRate = C;
				grade = "C";
			}
			
			System.out.println("신용등급>"+grade+"(추가이자율: "+ creditRate + "%)");
			Account ac = new HighCreditAccount(a, n, b, interest,grade);
			accounts[accCnt++] = ac;
			//신규계좌 생성 및 추가
			System.out.println("신규계좌 개설 완료");
		}
	}
	
	// 입    금
	public static void depositMoney()  {
		try {
		System.out.print("계좌번호: ");
		String a= scan.nextLine();
		Account acc = findAccount(a);
		if (acc == null) {
			System.out.println("해당 계좌가 존재하지 않습니다.");
			return;
		}
		
		System.out.print("입금액: ");
		int d = scan.nextInt();
		scan.nextLine();
		
		if (d <= 0) {
			System.out.println("음수는 입금할 수 없습니다.");
			return;
		}
		if (d % 500 != 0) {
			System.out.println("입금은 500원 단위로만 가능합니다.");
			return;
		}
		acc.deposit(d);
		System.out.println("예금이 성공되었습니다.");
//		findAccount(a).setBalance(findAccount(a).getBalance() + d);
		} catch (InputMismatchException e) {
			scan.nextLine();
			System.out.println("숫자만 입력하세요.");
		}
	}
		
	
	
	// 출    금
	public static void withdrawMoney() {
		try {
			System.out.print("계좌번호: ");
			String a= scan.nextLine();
			Account acc =findAccount(a);
			if (acc == null) return;
			
			System.out.print("출금액: ");
			int d = scan.nextInt();
			scan.nextLine();
			
			if (d <= 0) {
				System.out.println("음수는 출금할 수 없습니다.");
				return;
			}
			if (d % 1000 != 0) {
				System.out.println("출금은 1000원 단위로만 가능합니다.");
				return;
			}
			int remain = acc.getBalance() - d;
	//		int remain = findAccount(a).getBalance() - d;
			if (remain < 0) {
				System.out.println("잔고가 부족합니다. 금액전체를 출금할까요? (YES/NO)");
				String choice = scan.nextLine();
				if (choice.equalsIgnoreCase("YES")) {
					acc.setBalance(0);
					System.out.println("전액 출금처리 되었습니다.");
				}else {
					System.out.println("출금요청이 취소되었습니다.");
				}
//			System.out.println("출금 가능액을 초과하였습니다.");
			} else {
	//			findAccount(a).setBalance(remain);
				acc.setBalance(remain);
				System.out.println("출금이 성공되었습니다.");
			}
		}catch (InputMismatchException e) {
			scan.nextLine();
			System.out.println("숫자만 입력하세요.");
		}
	}
	
	
	// 전체계좌정보출력
	public static void showAccInfo() {
		for(int i=0 ; i<accCnt; i++) {
			//toString을 오버라이딩 했으므로 인스턴스를 그대로 출력
			System.out.println(accounts[i]);
		}
		System.out.println("*전체계좌정보가 출력됨*");
	}
	
	private static Account findAccount(String accNum) {
		for (int i=0; i< accCnt; i++) {
			if (accounts[i].getAccNum().equals(accNum)) {
				return accounts[i];
			}
		}
		System.out.println("해당 계좌를 찾을 수 없습니다.");
		return null;
	}
	public static int getMenuChoice() throws MenuSelectException {
		try {
			System.out.println("메뉴선택: ");
			int choice = Integer.parseInt(scan.nextLine());
			if (choice <1 || choice > 5) {
				throw new MenuSelectException("1~5 사이의 숫자만 입력 가능합니다.");
			}
			return choice;
		} catch (NumberFormatException e) {
			throw new MenuSelectException("숫자만 입력해야 합니다.");
		}
	}
	
}
