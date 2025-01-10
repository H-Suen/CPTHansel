import arc.*;
import java.awt.*;
import java.awt.image.*;

public class hanselCPT{
	public static void main(String[] args){
		Console con = new Console("BlackJack", 1280, 720);
		// creating variables and arrays
		int intCount;
		// creating arrays for player, dealer and deck
		int intDeck[][];
		int intPlayer[][];
		int intDealer[][];
		intDeck = new int[52][3];
		intDealer = new int[5][2];
		intPlayer = new int[5][2];
		int intPTotal = 0;
		int intDTotal = 0;
		// creating variables for the the bet and balance
		boolean blnName = false;
		int intBet = 0;
		int intBalance = 1000;
		String strName = "";
		// creating variables for shuffling
		int intRow;
		int intRow2;
		int intRand;
		int intTempNum;
		int intTempSuit;
		int intTempRand;
		int intPlayerCard;
		int intDealerCard;
		// creeating variables for translating numbers to cards
		int intSuit = 0;
		int intNum;
		// creating a variable for card count
		int intCardCount = 0;
		// creating a variable to print recents.txt
		String strScore;
		// creating variables to go to different screens and what the user does 
		String strScreen = "menu";
		String strAction;
		char chrAction;
		// creating variables to read from their text files that has their hand
		String strPlayerHand;
		String strDealerHand;
		int intReturn;
		// cereatng variables to draw an image
		int intX = 0;
		int intY = 0;
		int intXVel = 2;
		int intYVel = 2;
		BufferedImage imgDVD = con.loadImage("DVD.png");
		
		// while loop to constantly run this program
		while(true){
			// main menu
			while (strScreen.equals("menu")){
				con.clear();
				con.println("Welcome to Blackjack!");
				con.println("What would you like to do?");
				con.println("Play | Recent | Quit | Help");
				// get the action
				chrAction = con.getChar();
				// Play - go to the table screen
				if (chrAction == 'p'){
					con.clear();
					blnName = false;
					intBalance = 1000;
					strScreen = "table";
				}
				// Recent - read and print scores from winners.txt
				else if (chrAction == 'v'){
					con.clear();
					// read winners.txt and output it
					TextInputFile scores = new TextInputFile("winners.txt");
					while(scores.eof() == false){
						strScore = scores.readLine();
						con.println(strScore);
					}
					// get input to navigate to the menu or table
					con.println("Play | Menu");
					chrAction = con.getChar();
				}
				// Quit - Go to dvd screen
				else if (chrAction == 'q'){
					con.clear();
					strScreen = "quit";
				}
				// Help - Print out rules of the game
				else if (chrAction == 'h'){
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
					con.println("Main menu");
					chrAction = con.getChar();
				}
				// secret menu that prints a joke
				else if (chrAction == 's'){
					con.clear();
					con.println("Which company NEVER loses at blackjack?");
					con.sleep(1000);
					con.print(". ");
					con.sleep(1000);
					con.print(". ");
					con.sleep(1000);
					con.println(". ");
					con.sleep(1000);
					con.println("Forever 21");
					con.println("Return to main menu");
					chrAction = con.getChar();					
				}
			}
			
			// Quit screen
			while (strScreen.equalsIgnoreCase("quit")){
				// draw the image to the console
				con.drawImage(imgDVD, intX, intY);
				// change its location every iteration of the while loop
				intX += intXVel;
				intY += intYVel;
				// if it exceeds the border turn it back
				if (intX >= 1025 || intX <= 0){
					intXVel *= -1;
				}
				if (intY >= 465 || intY <= 0){
					intYVel *= -1;
				}
				// repaint the image
				con.repaint();
				// set a delay to make it enjoyable to see
				con.sleep(10);
			}
			
			// Table screen
			while (strScreen.equals("table")){
				// resetting the player and dealer total
				intPTotal = 0;
				intDTotal = 0;
				intSuit = 0;
				intCardCount = 0;
				con.clear();
				// asking for their name once
				while (blnName == false){
					con.print("Enter your name: ");
					strName = con.readLine();
					if (strName.equals("statitans")){
						con.clear();
						con.println("You gained an extra $1000");
						intBalance += 1000;
						con.sleep(1000);
					}
					else{
						blnName = true;
					}
				}
				// asking for their bet
				con.println("How much would you like to bet? ");
				con.println("Current Balance: " + intBalance);
				intBet = con.readInt();
				// failsafes for an invalid bet
				if (intBet <= 0){
					con.println("Your bet cannot be negative");
				}
				else if (intBalance < intBet){
					con.clear();
					con.println("Your bet should be lower than your balance");
				}
				// if the bet is the correct amount shuffle the deck
				else if (intBalance >= intBet && intBet > 0){
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
								// assigning the first variable to a temp variable
								intTempNum = intDeck[intRow2][0];
								intTempSuit = intDeck[intRow2][1];
								intTempRand = intDeck[intRow2][2];
								// making the first variable equal the second variable 
								intDeck[intRow2][0] = intDeck[intRow2+1][0];
								intDeck[intRow2][1] = intDeck[intRow2+1][1];
								intDeck[intRow2][2] = intDeck[intRow2+1][2];
								// making the second variable equal the temp variable
								intDeck[intRow2+1][0] = intTempNum;
								intDeck[intRow2+1][1] = intTempSuit;
								intDeck[intRow2+1][2] = intTempRand;
							}
						}
					}
					con.clear();
					// displaying player's hand
					con.println("Player 1's hand");
					// clearing the player's hand
					TextOutputFile PlayingHand = new TextOutputFile("PlayHand.txt");
					PlayingHand.close();
					// dealing the player the first cards
					for (intCount = 0; intCount < 2; intCount++){
						intPlayer[intCount][0] = intDeck[intCardCount][0];
						intPlayer[intCount][1] = intDeck[intCardCount][1];
						// converting royals and aces to the correct value
						if (intPlayer[intCount][0] >= 2 && intPlayer[intCount][0] <= 10){
							intPTotal += intPlayer[intCount][0];
						}
						else if (intPlayer[intCount][0] > 10){
							intPTotal += 10;
						}
						else if (intPlayer[intCount][0] == 1){
							intPTotal += 11;
							if (intPTotal >= 21){
								intPTotal -= 10;
							}
						}
						// counting how many cards have been drawn
						intCardCount++;
						// sending the cards to the method to convert the numbers to text
						intReturn = CPTtools.phand(intPlayer[intCount][0], intPlayer[intCount][1]);
					}
					// displaying text from the player's hand text file
					TextInputFile P1Hand = new TextInputFile("PlayHand.txt");
					while(P1Hand.eof() == false){
						strPlayerHand = P1Hand.readLine();
						con.println(strPlayerHand);
					}
					con.println("Total: " + intPTotal);
					P1Hand.close();
					
					// displaying dealers's hand
					con.println("");
					con.println("Dealer's hand");
					// clearing the dealers's hand
					TextOutputFile DealingHand = new TextOutputFile("DealHand.txt");
					DealingHand.close();
					// dealing the dealer the first cards
					for (intCount = 0; intCount < 2; intCount++){
						intDealer[intCount][0] = intDeck[intCardCount][0];
						intDealer[intCount][1] = intDeck[intCardCount][1];						
						intCardCount++;
						intReturn = CPTtools.dhand(intDealer[intCount][0], intDealer[intCount][1]);
						// adjusting the deck numbers into values for blackjack
						if (intDealer[intCount][0] >= 2 && intDealer[intCount][0] <= 10){
							intDTotal += intDealer[intCount][0];
						}
						else if (intDealer[intCount][0] > 10){
							intDTotal += 10;
						}
						else if (intDealer[intCount][0] == 1){
							intDTotal += 11;
							if (intDTotal >= 21){
								intDTotal -= 10;
							}
						}
					}
					// reading DealHand.txt and printing it out 
					TextInputFile DHand = new TextInputFile("DealHand.txt");
					strDealerHand = DHand.readLine();
					con.println(strDealerHand);
					DHand.close();					
					
					con.println("");
					// displaying that they have black jack
					if ((intPlayer[0][0] == 1 || intPlayer[1][0] == 1) &&  (intPlayer[0][0] >= 10 || intPlayer[1][0] >= 10)){
						intBalance += intBet * 3;
						con.println("YOU GOT A BLACK JACK!");
						con.println("YOU TRIPLED YOUR WINNINGS!");
						strScreen = "end";
					}
					// getting player action
					else{
						con.print("Would you like to: ");
						// if they have the correct total they can double down 
						if(intPTotal <= 11 && intPTotal >= 9){
							con.print("Double Down, ");
						}
						con.println("Stand, or Hit");
						chrAction = con.getChar();
						if (chrAction == 's'){
							strScreen = "dealer";
						}
						else if (chrAction == 'd'){
							strScreen = "double down";
							
						}
						else{
						strScreen = "hit";	
						}
					}
				}	
			}
			// double down screen
			while (strScreen.equalsIgnoreCase("double down")){
				con.clear();
				// displaying player's hand
				con.println("Player 1's hand");
				// dealing the player the card
				intPlayer[2][0] = intDeck[intCardCount][0];
				intPlayer[2][1] = intDeck[intCardCount][1];
				intCardCount++;
				// converting royals and aces to the correct value
				if (intPlayer[2][0] >= 2 && intPlayer[2][0] <= 10){
					intPTotal += intPlayer[2][0];
				}
				else if (intPlayer[2][0] > 10){
					if (intPlayer[2][0] > 10){
						intPTotal += 10;
					}
					else if (intPlayer[2][0] == 1){
						intPTotal += 11;
						if (intPTotal >= 21){
							intPTotal -= 10;
						}
					}
				}
				con.println("Total: " + intPTotal);
				intReturn = CPTtools.phand(intPlayer[2][0], intPlayer[2][1]);
				// displaying the player's cards
				TextInputFile P1Hand = new TextInputFile("PlayHand.txt");
				while(P1Hand.eof() == false){
					strPlayerHand = P1Hand.readLine();
					con.println(strPlayerHand);
				}
				P1Hand.close();
				// changing screen to dealer
				strScreen = "dealer";
			}
			
			// hit screen (gives them another card)
			while (strScreen.equalsIgnoreCase("hit")){
				con.clear();
				intCount = 2;
				intPlayer[intCount][0] = intDeck[intCardCount][0];
				intPlayer[intCount][1] = intDeck[intCardCount][1];
				// converting royals and aces to the correct value
				if (intPlayer[intCount][0] >= 2 && intPlayer[intCount][0] <= 10){
					intPTotal += intPlayer[intCount][0];
				}
				else if (intPlayer[intCount][0] > 10){
					intPTotal += 10;
				}
				else if (intPlayer[intCount][0] == 1){
					intPTotal += 11;
					if (intPTotal >= 21){
						intPTotal -= 10;
					}
				}

				intCardCount++;
				intReturn = CPTtools.phand(intPlayer[intCount][0], intPlayer[intCount][1]);
				
				// displaying player's hand
				con.println("Player 1's hand");
				TextInputFile P1Hand = new TextInputFile("PlayHand.txt");
				while(P1Hand.eof() == false){
					strPlayerHand = P1Hand.readLine();
					con.println(strPlayerHand);
				}
				con.println("Total: " + intPTotal);
				P1Hand.close();
				
				// displaying dealers's hand
				con.println("");
				con.println("Dealer's hand");
				TextInputFile DHand = new TextInputFile("DealHand.txt");
				strDealerHand = DHand.readLine();
				con.println(strDealerHand);
				DHand.close();		
				con.println("");

				if (intPTotal > 21){
					con.println("You busted!");
					strScreen = "end";
				}
				else if (intPTotal == 21){
					strScreen = "dealer";
				}
				else{
					con.println("Would you like to Hit or Stand?");
					chrAction = con.getChar();
					if (chrAction == 's'){
						strScreen = "dealer";
					}
				}
			}
			
			// when its the dealer turn
			while (strScreen.equalsIgnoreCase("dealer")){
				con.clear();
				// displaying the player's cards
				con.println("Player 1's hand");
				TextInputFile P1Hand = new TextInputFile("PlayHand.txt");
				while(P1Hand.eof() == false){
					strPlayerHand = P1Hand.readLine();
					con.println(strPlayerHand);
				}
				P1Hand.close();
				con.println("Total: " + intPTotal);
				con.println("");
				
				// displaying dealer's hand
				con.println("Dealer's hand");
				TextInputFile DHand = new TextInputFile("DealHand.txt");
				while(DHand.eof() == false){
					strDealerHand = DHand.readLine();
					con.println(strDealerHand);
				}
				
				if (intDTotal <= 16){
					intCount = 2;
					while (intDTotal <= 16){
						intDealer[intCount][0] = intDeck[intCardCount][0];
						intDealer[intCount][1] = intDeck[intCardCount][1];
						intCardCount++;
						intReturn = CPTtools.dhand(intDealer[intCount][0], intDealer[intCount][1]);
						// adjusting the deck numbers into values for blackjack
						if (intDealer[intCount][0] >= 2 && intDealer[intCount][0] <= 10){
							intDTotal += intDealer[intCount][0];
						}
						else if (intDealer[intCount][0] > 10){
							intDTotal += 10;
						}
						else if (intDealer[intCount][0] == 1){
							intDTotal += 11;    
							if (intDTotal >= 21){
								intDTotal -= 10;
							}
						}
						System.out.println(intDTotal);
					}
				}
				else if (intDTotal > 16 && intDTotal <= 21){ 
					if (intDTotal > intPTotal){
						con.println("");
						con.println("The dealer won");
						con.println("Better luck next time!");
						con.println("");
						strScreen = "end";
					}
					else if (intDTotal < intPTotal){
						con.println("");
						con.println("You won!");
						con.println("");
						intBalance += intBet * 2;
						strScreen = "end";
					}
					else if (intDTotal == intPTotal){
						con.println();
						con.println("You and the dealer tied!");
						con.println("You get you money back");
						con.println("");
						intBalance += intBet;
						strScreen = "end";
					}
				}
				else if (intDTotal > 21){
					con.println();
					con.println("The dealer busted!");
					con.println("You won!");
					intBalance += intBet * 2;
					strScreen = "end";
				}
			}
			
			// when both the player and the dealer have finished drawing cards
			while (strScreen.equalsIgnoreCase("end")){
				// displaying their current balance
				con.println("Current balance: " + intBalance);
				// if they run out of money
				if (intBalance <= 0){
					con.println("You have run out of money");
					con.println("Return to main menu");
					strAction = con.readLine();
					// write their names in winners.txt
					TextOutputFile loser = new TextOutputFile("winners.txt", true);
					loser.println(strName);
					loser.println(intBalance);
					loser.close();
					// sending them to menu screen
					strScreen = "menu";
				}
				else {
					con.println("What would you like to do?");
					con.println("Return to main menu | Play again");
					chrAction = con.getChar();
					if (chrAction == '0'){
						strScreen = "table";
					}
					else{
						TextOutputFile winner = new TextOutputFile("winners.txt", true);
						winner.println(strName);
						winner.println(intBalance);
						winner.close();
						strScreen = "menu";
					}
				}
			}
		}
	}
}
