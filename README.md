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

I tested the latest version and the 4.9.0 version, and the results were the same.  

### Some examples:
https://testnet.opbnbscan.com/tx/0x30c9c0a1aaf189f0332e0e0ef5a6f7bc4d80d18bcc9b6f24e64b838c7a9672c7  
https://testnet.opbnbscan.com/tx/0xfc6610c358bcd8f3a32d3a497586d98fe94cc1e317667d280834f15a837b73ee  
https://testnet.opbnbscan.com/tx/0xac76803602ebbe5503eaa02370a0c19f4c5599e1f33d1f3ec5cb81f226d91b69  

ERC20 example:
https://testnet.opbnbscan.com/tx/0xe2f16e5f26f579600869e7c16e7ec2939363dc58f7488b1136af8dc29190b32b
