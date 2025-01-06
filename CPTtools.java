import arc.*;

public class CPTtools{
	public static int phand(int intNum, int intSuit){
		int intReturn = 0;
		// opening a txt file to store player's hand 
		TextOutputFile PlayerHand = new TextOutputFile("PlayHand.txt", true);		
		// Translating and printing numbers
		// translating 11-13 to royals
		if (intNum == 1 || intNum > 10){
			if(intNum == 11){
				PlayerHand.print("Jack of ");
			}
			else if(intNum == 12){
				PlayerHand.print("Queen of ");
			}
			else if(intNum == 13){
				PlayerHand.print("King of ");
			}
			else if(intNum == 1){
				PlayerHand.print("Ace of ");
			}
		}
		else if (intNum >= 2 && intNum <= 10){
			PlayerHand.print(intNum + " of ");
		}
		// translating and printing suits
		if(intSuit == 1){
			PlayerHand.println("Diamonds");
		}
		else if(intSuit == 2){
			PlayerHand.println("Clubs");							
		}
		else if(intSuit == 3){
			PlayerHand.println("Hearts");
		}
		else if(intSuit == 4){
			PlayerHand.println("Spades");
		}
		PlayerHand.close();
		return intReturn;
	}
	public static int dhand(int intNum, int intSuit){
		int intReturn = 0;
		// opening a txt file to store player's hand 
		TextOutputFile DealerHand = new TextOutputFile("DealHand.txt", true);		
		// Translating and printing numbers
		// translating 11-13 to royals
		if (intNum == 1 || intNum > 10){
			if(intNum == 11){
				DealerHand.print("Jack of ");
			}
			else if(intNum == 12){
				DealerHand.print("Queen of ");
			}
			else if(intNum == 13){
				DealerHand.print("King of ");
			}
			else if(intNum == 1){
				DealerHand.print("Ace of ");
			}
		}
		else if (intNum >= 2 && intNum <= 10){
			DealerHand.print(intNum + " of ");
		}
		// translating and printing suits
		if(intSuit == 1){
			DealerHand.println("Diamonds");
		}
		else if(intSuit == 2){
			DealerHand.println("Clubs");							
		}
		else if(intSuit == 3){
			DealerHand.println("Hearts");
		}
		else if(intSuit == 4){
			DealerHand.println("Spades");
		}
		DealerHand.close();
		return intReturn;
	}
}
