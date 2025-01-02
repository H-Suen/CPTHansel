import arc.*;

public class hanselCPT{
	public static void main(String[] args){
		Console con = new Console();
		
		// creating variables and arrays
		int intDeck[][];
		int intPlayer[][];
		int intDealer[][];
		intDeck = new int[52][3];
		intDealer = new int[5][2];
		intPlayer = new int[5][2];
		int intBet;
		int intBalance = 1000;
		int intRow;
		int intRow2;
		int intRand;
		int intSuit = 0;
		int intNum;
		int intTempNum;
		int intTempSuit;
		int intTempRand;
		int intPlayerCard;
		int intDealerCard;
		String strScore;
		String strScreen = "menu";
		String strAction;
		
		
		// main menu
		while (strScreen.equals("menu")){
			con.clear();
			con.println("welcome to Blackjack!");
			con.println("What would you like to do?");
			con.println("Play | Recent | Quit | Help");
			// get the action
			strAction = con.readLine();
			// Play - go to the table screen
			if (strAction.equalsIgnoreCase("play")){
				con.clear();
				strScreen = "table";
			}
			// Recent - read and print scores from winners.txt
			else if (strAction.equalsIgnoreCase("recent")){
				con.clear();
				// read winners.txt and output it
				TextInputFile scores = new TextInputFile("winners.txt");
				while(scores.eof() == false){
					strScore = scores.readLine();
					con.println(strScore);
				}
				// Doesn't matter what they type this is just used 
				// to make sure the scores do not flash then go to the main screen
				con.println("Go back");
				strAction = con.readLine();
			}
			// Quit - Go to dvd screen
			else if (strAction.equalsIgnoreCase("quit")){
				con.clear();
				strScreen = "DVD";
			}
			// Help - Print out rules of the game
			else if (strAction.equalsIgnoreCase("help")){
				con.clear();
				con.println("The goal of Blackjack is simple.");
				con.println("All you need to do is have a higher hand value than the dealer,");
				con.println("WITHOUT going over 21.");
				con.println("You are dealt 2 cards and can choose to:");
				con.println("HIT: recieve an additional card");
				con.println("STAND: keep your current hand");
				con.println("DOUBLE DOWN: (you can only do this when your first two cards total 9 to 11)");
				con.println("Once you double down, you double your bet and you are dealt one more card.");
				con.println("This means you can increase the amount you win but you could lose more money");
				con.println("The dealer has 2 cards, but only one is face up");
				con.println("");
				con.println("If your hand exceeds 21, you BUST and lose the game");
				con.println("If the dealer bust, you WIN!");
				con.println("If neither you nor the dealer busts, the person with the highest hand wins");
				con.println("");
				con.println("Play | Main menu");
				strAction = con.readLine();
			}
		}
		
		// Table screen
		while (strScreen.equals("table")){
			con.println("How much would you like to bet?");
			con.println("Current Balance: " + intBalance);
			intBet = con.readInt();
			if (intBet <= 0){
				con.println("Your bet cannot be negative");
			}
			else if (intBalance <= intBet){
				con.println("Your bet should be lower than your balance");
			}
			else if (intBalance >= intBet && intBet > 0){
				if (intPlayer[0][0] + intPlayer[1][0] + intPlayer[2][0] + intPlayer[3][0] + intPlayer[4][0] <= 21){
					// Removing the bet amount from balance
					intBalance -= intBet; 
				// making each card unique with numbers and suits
					for (intNum = 0; intNum < 52; intNum++){
							intDeck[intNum][0] = ((intNum) % 13) + 1;
							if (intDeck[intNum][0] == 1){
								intSuit++;
							}
							intDeck[intNum][1] = intSuit;
						}
					// assign each card a random number
					for (intRow = 0; intRow < 52; intRow++){
						intDeck[intRow][2] = 1 + (int)(Math.random()*((100-1) + 1));
					}
					// orginize the numbers from lowest to highest
					for (intRow = 0; intRow < 51; intRow++){
						for (intRow2 = 0; intRow2 < 51 - intRow; intRow2++){
							if (intDeck[intRow2][2] > intDeck[intRow2+1][2]){
								intTempNum = intDeck[intRow2][0];
								intTempSuit = intDeck[intRow2][1];
								intTempRand = intDeck[intRow2][2];
								intDeck[intRow2][0] = intDeck[intRow2+1][0];
								intDeck[intRow2][1] = intDeck[intRow2+1][1];
								intDeck[intRow2][2] = intDeck[intRow2+1][2];
								intDeck[intRow2+1][0] = intTempNum;
								intDeck[intRow2+1][1] = intTempSuit;
								intDeck[intRow2+1][2] = intTempRand;
							}
						}
					}
					// dealing the player the first cards
					intPlayer[0][0] = intDeck[0][0];
					intPlayer[0][1] = intDeck[0][1];
					intPlayer[1][0] = intDeck[1][0];
					intPlayer[1][1] = intDeck[1][1];
					con.clear();
					
					// displaying player's hand
					con.println("Player 1's hand");
					for (intPlayerCard = 0; intPlayerCard < 2; intPlayerCard++){				
						// Translating and printing numbers
						// translating 11-13 to royals
						if (intPlayer[intPlayerCard][0] == 1 || intPlayer[intPlayerCard][0] > 10){
							if(intPlayer[intPlayerCard][0] == 11){
								con.print("Jack of ");
							}
							else if(intPlayer[intPlayerCard][0] == 12){
								con.print("Queen of ");
							}
							else if(intPlayer[intPlayerCard][0] == 13){
								con.print("King of ");
							}
							else if(intDealer[intPlayerCard][0] == 1){
								con.print("Ace of");
							}
						}
						else if (intPlayer[intPlayerCard][0] >= 2 && intPlayer[intPlayerCard][0] <= 10){
							con.print(intPlayer[intPlayerCard][0] + " of ");
						}
						// translating and printing suits
						if(intPlayer[intPlayerCard][1] == 1){
							con.println("Diamonds");
						}
						else if(intPlayer[intPlayerCard][1] == 2){
							con.println("Clubs");
						}
						else if(intPlayer[intPlayerCard][1] == 3){
							con.println("Hearts");
						}
						else if(intPlayer[intPlayerCard][1] == 4){
							con.println("Spades");
						}
					}

					// Getting Dealer's hand
					intDealer[0][0] = intDeck[2][0];
					intDealer[0][1] = intDeck[2][1];
					// Displaying Dealer's hand
					con.println("");
					con.println("Dealer's hand");
					for (intDealerCard = 0; intDealerCard < 1; intDealerCard++){				
						// Translating and printing numbers
						// translating 11-13 to royals
						if (intDealer[intDealerCard][0] == 1 || intDealer[intDealerCard][0] > 10){
							if(intDealer[intDealerCard][0] == 11){
								con.print("Jack of ");
							}
							else if(intDealer[intDealerCard][0] == 12){
								con.print("Queen of ");
							}
							else if(intDealer[intDealerCard][0] == 13){
								con.print("King of ");
							}
							else if(intDealer[intDealerCard][0] == 1){
								con.print("Ace of");
							}
						}
						else if (intDealer[intDealerCard][0] >= 2 && intDealer[intDealerCard][0] <= 10){
							con.print(intDealer[intDealerCard][0] + " of ");
						}
						// translating and printing suits
						if(intDealer[intDealerCard][1] == 1){
							con.println("Diamonds");
						}
						else if(intDealer[intDealerCard][1] == 2){
							con.println("Clubs");
						}
						else if(intDealer[intDealerCard][1] == 3){
							con.println("Hearts");
						}
						else if(intDealer[intDealerCard][1] == 4){
							con.println("Spades");
						}
					}
					
					
					con.println("");
					// displaying that they have black jack
					if ((intPlayer[0][0] == 1 || intPlayer[1][0] == 1) &&  (intPlayer[0][0] >= 10 || intPlayer[1][0] >= 10)){
						intBalance += intBet * 3;
						con.println("YOU GOT A BLACK JACK!");
						con.println("YOU TRIPLED YOUR WINNINGS!");
						con.println("Current Balance: " + intBalance);
					}
					// getting player action
					else{
						con.print("Would you like to: ");
						
						if(intPlayer[0][0] + intPlayer[1][0] <= 11 && intPlayer[0][0] + intPlayer[1][0] >= 9){
							con.print("Double Down, ");
						}
						con.println("Stand, or Hit");
						strScreen = con.readLine();
						
					
					}
				}
			}	
		}
		while (strScreen.equalsIgnoreCase("double down")){
		
		}		
	}
}
