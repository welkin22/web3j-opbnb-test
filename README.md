I tested whether the https://github.com/hyperledger/web3j project in my local environment is compatible with the opBNB chain. I found that it is fully compatible with the opBNB chain.

# How to run
Running `gradle shadowJar` can get a complete Jar package for execution at `build/libs/Web3App-0.1.0-all.jar`.
By executing the following command, you can see the test results:
```
# deploy helloWorld contract
WEB3J_NODE_URL=https://opbnb-testnet-rpc.bnbchain.org WEB3J_PRIVATE_KEY=${yourprivatekey} java -jar ./build/libs/Web3App-0.1.0-all.jar contract_deploy
# call helloWorld contract
WEB3J_NODE_URL=https://opbnb-testnet-rpc.bnbchain.org WEB3J_PRIVATE_KEY=${yourprivatekey} java -jar ./build/libs/Web3App-0.1.0-all.jar contract_call ${your_contract_address}
# simple transfer 1 eth
WEB3J_NODE_URL=https://opbnb-testnet-rpc.bnbchain.org WEB3J_PRIVATE_KEY=${yourprivatekey} java -jar ./build/libs/Web3App-0.1.0-all.jar simple_transfer ${to_address} 1
# raw transfer 1 eth
WEB3J_NODE_URL=https://opbnb-testnet-rpc.bnbchain.org WEB3J_PRIVATE_KEY=${yourprivatekey} java -jar ./build/libs/Web3App-0.1.0-all.jar raw_transfer ${to_address} 1
```
Remember to replace yourprivatekey, your_contract_address, to_address, etc. with your own addresses.
You can also test by running the main function directly in the IDE, located at java/org/web3j/Web3App.java
