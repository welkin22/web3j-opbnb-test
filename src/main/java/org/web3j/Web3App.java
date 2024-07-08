package org.web3j;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.generated.contracts.HelloWorld;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <p>This is the generated class for <code>web3j new helloworld</code></p>
 * <p>It deploys the Hello World contract in src/main/solidity/ and prints its address</p>
 * <p>For more information on how to run this project, please refer to our <a href="https://docs.web3j.io/latest/command_line_tools/#running-your-application">documentation</a></p>
 */
public class Web3App {

    private static final String nodeUrl = System.getenv().getOrDefault("WEB3J_NODE_URL", "<node_url>");
    private static final String walletPassword = System.getenv().getOrDefault("WEB3J_WALLET_PASSWORD", "<wallet_password>");
    private static final String walletPath = System.getenv().getOrDefault("WEB3J_WALLET_PATH", "<wallet_path>");
    private static final String PRIVATE_KEY = System.getenv().getOrDefault("WEB3J_PRIVATE_KEY", "<private_key>");

    public static void main(String[] args) throws Exception {
        if (args.length<1){
            throw new RuntimeException("need command arg");
        }
        Web3j web3j = Web3j.build(new HttpService(nodeUrl));
        String commend = args[0];
        switch (commend){
            case "contract_deploy":
                HelloWorldContractDeploy(web3j);
                break;
            case "contract_call":
                HelloWorldContractCall(web3j,args[1]);//args[1]=hello world contract address
                break;
            case "simple_transfer":
                SimpleTransfer(web3j,args[1],args[2]);//args[1]=to address, args[2]=transfer value in eth. For example: "0.1" means 0.1eth
                break;
            case "raw_transfer":
                RawTransfer(web3j,args[1],args[2]);//same with simple_transfer
                break;
            default:
                throw new RuntimeException("Unsupported commend type");
        }
    }

    private static void RawTransfer(Web3j web3j, String to, String ether) throws Exception{
        Credentials credentials = Credentials.create(PRIVATE_KEY);
        BigInteger nonce = web3j.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getTransactionCount();
        System.out.println("nonce:"+nonce);
        EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
        EthEstimateGas estimateGas = web3j.ethEstimateGas(Transaction.createEtherTransaction(credentials.getAddress(),
                nonce, ethGasPrice.getGasPrice(), BigInteger.valueOf(50000000), to, Convert.toWei(new BigDecimal(ether),
                        Convert.Unit.ETHER).toBigInteger())).send();
        RawTransaction etherTransaction = RawTransaction.createEtherTransaction(
                nonce,
                ethGasPrice.getGasPrice(),
                estimateGas.getAmountUsed(),
                to,
                Convert.toWei(new BigDecimal(ether), Convert.Unit.ETHER).toBigInteger());
        byte[] signMessage = TransactionEncoder.signMessage(etherTransaction, credentials);
        EthSendTransaction sendTransaction = web3j.ethSendRawTransaction(Numeric.toHexString(signMessage)).send();
        System.out.println("tx:" + sendTransaction.getTransactionHash());
        System.out.println("isSuccess:" + sendTransaction.getResult());
    }

    private static void SimpleTransfer(Web3j web3j, String to, String ether) throws Exception{
        TransactionReceipt result = Transfer.sendFundsEIP1559(web3j, Credentials.create(PRIVATE_KEY),
                to,
                new BigDecimal(ether),
                Convert.Unit.ETHER,
                BigInteger.valueOf(21000),
                Convert.toWei(BigDecimal.valueOf(3), Convert.Unit.GWEI).toBigInteger(),
                Convert.toWei(BigDecimal.valueOf(3), Convert.Unit.GWEI).toBigInteger()
        ).send();
        System.out.println("tx:" + result.getTransactionHash());
        System.out.println("isSuccess:" + result.getStatus());
    }

    private static void HelloWorldContractDeploy(Web3j web3j) throws Exception{
        System.out.println("Deploying HelloWorld contract ...");
        HelloWorld helloWorld = HelloWorld.deploy(web3j, Credentials.create(PRIVATE_KEY), new DefaultGasProvider(), "Hello Blockchain World!").send();
        System.out.println("Contract address: " + helloWorld.getContractAddress());
    }

    private static void HelloWorldContractCall(Web3j web3j, String contractAddress) throws Exception{
        System.out.println("Deploying HelloWorld contract ...");
        HelloWorld helloWorldContract = HelloWorld.load(contractAddress, web3j, Credentials.create(PRIVATE_KEY), new DefaultGasProvider());
        System.out.println("Contract address: " + helloWorldContract.getContractAddress());
        System.out.println("Greeting method result: " + helloWorldContract.greeting().send());
    }


}

