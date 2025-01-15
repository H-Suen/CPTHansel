import arc.*;
import java.awt.*;
import java.awt.image.*;

public class hanselCPT{
	public static void main(String[] args){
		Console con = new Console("BlackJack", 1280, 720);
		// creating variables and arrays
		int intCount = 0;
		// creating arrays for player, dealer and deck
		int intDeck[][];
		int intPlayer[][];
		int intDealer[][];
		intDeck = new int[52][3];
		intDealer = new int[5][2];
		intPlayer = new int[5][2];
		int intPTotal = 0;
		int intDTotal = 0;
		// variables for leaderboard
		int intLength = 0;
		String strScore [][];
		String strTemp[];
		// creating variables for the the bet and balance
		boolean blnName = false;
		int intBet = 0;
		int intBalance = 1000;
		String strName = "";
		// creating variables for shuffling
		int intRow;
		int intRow2;
		int intRand;
		int intTempCard[];
		int intPlayerCard;
		int intDealerCard;
		// creating variables for translating numbers to cards
		int intSuit = 0;
		int intNum;
		// creating a variable for card count
		int intCardCount = 0;
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
				con.println("(p)lay | (v)iew Recents | (q)uit | (h)elp");
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
					strTemp = new String[1];
					// read winners.txt and output it
					TextInputFile scores = new TextInputFile("winners.txt");
					while(scores.eof() == false){
						strTemp[0] = scores.readLine();
						intLength++;
					}
					scores.close();
					strScore = new String[intLength/2][2];
					TextInputFile score = new TextInputFile("winners.txt");
					while(score.eof() ==  false){
						strScore[intCount][0] = score.readLine();
						strScore[intCount][1] = score.readLine();
						intCount++;
					}
					for (intRow2 = 0; intRow2 < intLength / 2 - 1; intRow2++){
						// for the for loop to run until all item are sorted
						for (intRow = 0; intRow < intLength / 2 - 1 - intRow2; intRow++){
							if(Integer.parseInt(strScore[intRow][1]) > Integer.parseInt(strScore[intRow+1][1])){
								// take the left item 
								strTemp = strScore[intRow];
								// move right item to the left 
								strScore[intRow] = strScore[intRow+1];
								// put temp values into right item
								strScore[intRow+1] = strTemp;
							}
						}
					}
				for (intRow = 0; intRow < intCount; intRow++){
					con.println(strScore[intRow][0] + " | " + strScore[intRow][1]);
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
				intCount++;
				if (intCount >= 50000){
					con.closeConsole();
				}
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
					// if they input the secret password
					if (strName.equals("statitans")){
						con.clear();
						// give them an extra 1000 money
						con.println("You gained an extra $1000");
						intBalance += 1000;
						con.sleep(1000);
					}
					else if (strName.equals("")){
						// if they enter a blank name it breaks the leaderboard code
						con.println("Invalid name");
					}
					else{
						// make sure that they only need to enter name once per time they play
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
								intTempCard = intDeck[intRow2];
								// making the first variable equal the second variable 
								intDeck[intRow2] = intDeck[intRow2+1];
								// making the second variable equal the temp variable
								intDeck[intRow2+1] = intTempCard;
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
						// if they want to stand or hit
						con.println("Stand, or Hit");
						chrAction = con.getChar();
						// set the screen to dealer if they stand
						if (chrAction == 's'){
							strScreen = "dealer";
						}
						// set the screen to double down
						else if (chrAction == 'd'){
							strScreen = "double down";
							
						}
						// else give them a card
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
				// counting the amount of cards dealt
				intCardCount++;
				// sending the hand to the method to convert it to user friendly text
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
				// telling user if they busted, got 21 or they want to hit again
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
				DHand.close();
				// giving dealer cards until they go over 16
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
					}
				}
				// if the dealer is higher than 16 and hasn't busted
				else if (intDTotal > 16 && intDTotal <= 21){ 
					// if the dealer's hand is greater than the player
					if (intDTotal > intPTotal){
						con.println("");
						con.println("The dealer won");
						con.println("Better luck next time!");
						con.println("");
						strScreen = "end";
					}
					// if the player's hand is greater than the dealer's hand
					else if (intDTotal < intPTotal){
						con.println("");
						con.println("You won!");
						con.println("");
						// giving the player gained money
						intBalance += intBet * 2;
						strScreen = "end";
					}
					// if the plaer and the dealer's hand tie
					else if (intDTotal == intPTotal){
						con.println();
						con.println("You and the dealer tied!");
						con.println("You get you money back");
						con.println("");
						// giving back the money they bet
						intBalance += intBet;
						strScreen = "end";
					}
				}
				// if the dealer busted
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
				// asking them what to do
				else{
					con.println("What would you like to do?");
					con.println("(r)eturn to main menu | (p)lay again");
					chrAction = con.getChar();
					// if they want to play agian
					if (chrAction == 'p'){
						strScreen = "table";
					}
					// if they want to go to the main menu
					else{
						// write their balance and name to the winners.txt
						TextOutputFile winner = new TextOutputFile("winners.txt", true);
						winner.println(strName);
						winner.println(intBalance);
						winner.close();
						// send the user back to menu
						strScreen = "menu";
					}
				}
			}
		}
	}
}
