import arc.*;

public class CPTmethods{
	public static int phand(int intdeck){
		int intSuit = 0;
		int intNum;
		int intRand;
		// making each card unique with numbers and suits
		for (int i = 0; i < 52; i++){
				intdeck[i][0] = ((i) % 13) + 1;
				if (intdeck[i][0] == 1){
					intSuit++;
				}
				intdeck[i][1] = intSuit;
			}
		// assign each card a random number
		for (int intRow = 0; intRow < 52; intRow++){
			intdeck[intRow][2] = 1 + (int)(Math.random()*((100-1) + 1));
		}
		// orginize the numbers from lowest to highest
		for (int intRow = 0; intRow < 51; intRow++){
			for (int intRow2 = 0; intRow2 < 51 - intRow; intRow2++){
				if (intdeck[intRow2][2] > intdeck[intRow2+1][2]){
					intNum = intdeck[intRow2][0];
					intSuit = intdeck[intRow2][1];
					intRand = intdeck[intRow2][2];
					intdeck[intRow2][0] = intdeck[intRow2+1][0];
					intdeck[intRow2][1] = intdeck[intRow2+1][1];
					intdeck[intRow2][2] = intdeck[intRow2+1][2];
					intdeck[intRow2+1][0] = intNum;
					intdeck[intRow2+1][1] = intSuit;
					intdeck[intRow2+1][2] = intRand;
				}
			}
		}
		return intdeck;
	}
}
