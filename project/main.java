import java.util.ArrayList;
import java.util.HashMap;


class Card {
    public String suit;
    public String value;
    public Integer intValue;
    
    public Card(String suit, String value, Integer intValue) {
        this.suit = suit;
        this.value = value;
        this.intValue = intValue;
    }
    
    // カードを表示する
    public String getCardString() {
        return this.suit + this.value + "(" + this.intValue + ")";
    }
}

class Deck {
    public ArrayList<Card> deck;
    
    public Deck() {
        this.deck = generateDeck();
    }
    
    // デッキ(山札)を作成
    public static ArrayList<Card> generateDeck() {
        ArrayList<Card> newDeck = new ArrayList<>();
        String[] suits = new String[]{"♣", "♦", "♥", "♠"};
        String[] values = new String[]{"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < values.length; j++) {
                newDeck.add(new Card(suits[i], values[j], j + 1));
            }
        }
        return newDeck;
    }

    public void printDeck() {
        System.out.println("Desplaying cards...");
        for (int i = 0; i < this.deck.size(); i++) {
            System.out.println(this.deck.get(i).getCardString());
        }
    }

    public void shuffleDeck() {
        for (int i = this.deck.size()-1; i >= 0; i--) {
            int j = (int)Math.floor(Math.random() * (i + 1));
            Card tmp = this.deck.get(i);
            this.deck.set(i, this.deck.get(j));
            this.deck.set(j, tmp);
        }
    }

    public Card draw() {
        return this.deck.remove(this.deck.size() - 1);
    }
}

class Table {
    public int amountOfPlayers;
    public String gameMode;

    public Table(int amountOfPlayers, String gameMode) {
        this.amountOfPlayers = amountOfPlayers;
        this.gameMode = gameMode;
    }
}

class Dealer {
    public static ArrayList<ArrayList<Card>> startGame(Table table) {
        Deck deck = new Deck();
        deck.shuffleDeck();

        ArrayList<ArrayList<Card>> playerCards = new ArrayList<>();

        for (int i = 0; i < table.amountOfPlayers; i++) {
            ArrayList<Card> playerHand = new ArrayList<>();
            for (int j = 0; j < Dealer.initialCards(table.gameMode); j++) {
                Card card = deck.draw();
                playerHand.add(card);
            }
            playerCards.add(playerHand);
        }
        return playerCards;
    }

    // gameModeによって配る枚数を変更
    public static int initialCards(String gameMode) {
        if (gameMode == "poker") return 5;
        if (gameMode == "21") return 2;
        if (gameMode == "Pair of Cards") return 5;
        else return 0;
    }

    // テーブルの状態を表示
    public static void printTableInformation(ArrayList<ArrayList<Card>> playerCards, Table table) {
        System.out.println("Amount of players: " + table.amountOfPlayers + " Game mode: " + table.gameMode);

        for (int i = 0; i < playerCards.size(); i++) {
            System.out.println("Player " + (i + 1) + " hand is: ");
            for (int j = 0; j < playerCards.get(i).size(); j++) {
                System.out.println(playerCards.get(i).get(j).getCardString());
            }
            System.out.println();
        }
    }

    // ゲームの勝敗決定
    public static String winnerPairOfCards(ArrayList<ArrayList<Card>> playerCards, Table table) {
        final int[] cardPower = new int[]{1, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] numbers1 = Helperfunction.createNumberArr(playerCards.get(0));
        int[] numbers2 = Helperfunction.createNumberArr(playerCards.get(1));
        
        HashMap<Integer, Integer> hashmap1 = Helperfunction.createHashMap(cardPower, numbers1);
        HashMap<Integer, Integer> hashmap2 = Helperfunction.createHashMap(cardPower, numbers2);

        String winner = "draw";
        int pairOfCards = 0;

        for (int i = 0; i < cardPower.length; i++) {
            if (hashmap1.get(cardPower[i]) > hashmap2.get(cardPower[i])) {
                if (pairOfCards < hashmap1.get(cardPower[i])) {
                    winner = "Player1";
                    pairOfCards = hashmap1.get(cardPower[i]);
                }
            }
            else if (hashmap1.get(cardPower[i]) < hashmap2.get(cardPower[i])) {
                if (pairOfCards < hashmap2.get(cardPower[i])) {
                    winner = "Player2";
                    pairOfCards = hashmap2.get(cardPower[i]);
                }
            }
        }
        System.out.println("The winner of this game is ");
        return winner;
    }
}

class Helperfunction {
    // 数字だけの配列を作成
    public static int[] createNumberArr(ArrayList<Card> playerhand) {
        int[] intArr = new int[playerhand.size()];
        for (int i = 0; i < playerhand.size(); i++) {
            intArr[i] = playerhand.get(i).intValue;
        }
        return intArr;
    }

    // Hashmapを作成
    public static HashMap<Integer, Integer> createHashMap(int[] cardPower, int[] numberArr) {
        HashMap<Integer, Integer> hashmap = new HashMap<>();
        for (int i = 0; i < cardPower.length; i++) {
            hashmap.put(cardPower[i], 0);
        }

        for (int i = 0; i< numberArr.length; i++) {
            hashmap.replace(numberArr[i], hashmap.get(numberArr[i]) + 1);
        }
        return hashmap;
    }
}

class Main {
    public static void main(String[] args){
        Table table = new Table(2, "Pair of Cards");
        ArrayList<ArrayList<Card>> game = Dealer.startGame(table);
        Dealer.printTableInformation(game, table);
        System.out.println(Dealer.winnerPairOfCards(game, table));
    }
}