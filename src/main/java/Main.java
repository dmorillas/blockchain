import co.morillas.blockchain.Block;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Main {
    private static String DIFFICULTY_STRING = "00000";
    private static String GENESIS_HASH = "genesis";

    private static ArrayList<Block> blockchain = new ArrayList<Block>();

    public static void main(String[] args) {
        blockchain.add(new Block(GENESIS_HASH));
        System.out.println("Trying to Mine block 1... ");
        blockchain.get(0).mineBlock(DIFFICULTY_STRING);

        blockchain.add(new Block(blockchain.get(blockchain.size()-1).getHash()));
        System.out.println("Trying to Mine block 2... ");
        blockchain.get(1).mineBlock(DIFFICULTY_STRING);

        blockchain.add(new Block(blockchain.get(blockchain.size()-1).getHash()));
        System.out.println("Trying to Mine block 3... ");
        blockchain.get(2).mineBlock(DIFFICULTY_STRING);

        System.out.println("\nBlockchain is Valid: " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);
    }

    private static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;

        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);

            if(!currentBlock.getHash().equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                return false;
            }

            if(!previousBlock.getHash().equals(currentBlock.getPreviousHash()) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }

            if(!currentBlock.getHash().startsWith(DIFFICULTY_STRING)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }

    private void addAndValidateBlock(Block block) {
        // compare previous block hash back to genesis hash
        Block current = block;
        for (int i = blockchain.size() - 1; i >= 0; i--) {
            Block b = blockchain.get(i);
            if (b.getHash().equals(current.getPreviousHash())) {
                current = b;
            } else {
                throw new RuntimeException("Block Invalid");
            }
        }
        this.blockchain.add(block);
    }
}
