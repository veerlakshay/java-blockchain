# Blockchain - Blockchain Project

## Project Overview

In this project:
- Created a basic blockchain structure.
- Implemented a simple proof-of-work mining system.
- Exploreed how data integrity is maintained in a blockchain.

## Project Structure

1. **Creating Blocks**:
   - Each block in BlockChain has:
     - A `hash`, a unique digital fingerprint.
     - A `previousHash` to link to the previous block.
     - `data` to store information (e.g., transactions).

2. **Generating Hashes**:
   - Each block’s hash is generated using SHA256. Modifying data in a block changes its hash, which affects the entire chain, making tampering evident.

3. **Mining and Proof of Work**:
   - Blocks are mined by finding a hash that starts with a specific number of zeroes. This ensures that adding blocks requires time and computational effort.
   - Used a `mineBlock()` method that attempts different `nonce` values until a valid hash is found.

4. **Chain Validation**:
   - We implement an `isChainValid()` method to check that all hashes and links are intact, confirming that no data in the blockchain has been altered.
