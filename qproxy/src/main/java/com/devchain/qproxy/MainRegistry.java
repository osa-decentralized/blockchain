package com.devchain.qproxy;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.2.0.
 */
public class MainRegistry extends Contract {
    private static final String BINARY = "6060604052341561000c57fe5b5b600160a060020a0333166000908152602081905260409020805460ff191660011790555b5b6109a4806100416000396000f300606060405236156100725763ffffffff60e060020a60003504166305db9fa681146100885780631785f53c146100ff5780633031a2ac1461011d57806331a6002e1461008857806370480275146101c25780638490983c146101e0578063d188f36b1461024b578063d937254614610286575b341561007a57fe5b6100865b60006000fd5b565b005b341561009057fe5b6100a4600160a060020a03600435166102f1565b60408051602080825283518183015283519192839290830191858101910280838382156100ec575b8051825260208311156100ec57601f1990920191602091820191016100cc565b5050509050019250505060405180910390f35b341561010757fe5b610086600160a060020a0360043516610396565b005b341561012557fe5b610086600160a060020a0360043581169060243581169060443590606435166103e2565b005b341561009057fe5b6100a4600160a060020a03600435166102f1565b60408051602080825283518183015283519192839290830191858101910280838382156100ec575b8051825260208311156100ec57601f1990920191602091820191016100cc565b5050509050019250505060405180910390f35b34156101ca57fe5b610086600160a060020a036004351661071c565b005b34156101e857fe5b6100a461076b565b60408051602080825283518183015283519192839290830191858101910280838382156100ec575b8051825260208311156100ec57601f1990920191602091820191016100cc565b5050509050019250505060405180910390f35b341561025357fe5b61026a600435600160a060020a03602435166107e6565b60408051600160a060020a039092168252519081900360200190f35b341561028e57fe5b6100a46108a0565b60408051602080825283518183015283519192839290830191858101910280838382156100ec575b8051825260208311156100ec57601f1990920191602091820191016100cc565b5050509050019250505060405180910390f35b6102f961091b565b600160a060020a03331660009081526020819052604090205460ff1615156001141561038f57600160a060020a0382166000908152600160209081526040918290208054835181840281018401909452808452909183018282801561038757602002820191906000526020600020905b8154600160a060020a03168152600190910190602001808311610369575b505050505090505b5b5b919050565b600160a060020a03331660009081526020819052604090205460ff161515600114156103dd57600160a060020a0381166000908152602081905260409020805460ff191690555b5b5b50565b600160a060020a0333166000908152602081905260408120548190819060ff1615156001141561066b57600092506002856002896000604051602001526040518082600160a060020a0316600160a060020a0316606060020a02815260140191505060206040518083038160008661646e5a03f1151561045e57fe5b50506040805180516000602092830181905283519485528483019190915282518484019492939092839003019082908661646e5a03f1151561049c57fe5b50506040518051905091506002856002886000604051602001526040518082600160a060020a0316600160a060020a0316606060020a02815260140191505060206040518083038160008661646e5a03f115156104f557fe5b50506040805180516000602092830181905283519485528483019190915282518484019492939092839003019082908661646e5a03f1151561053357fe5b50506040805151600084815260036020529190912054909150600160a060020a03848116911614801561057f5750600081815260036020526040902054600160a060020a038481169116145b1561007e5760008281526003602090815260408083208054600160a060020a0319908116600160a060020a038a811691821790935586865283862080549092161790558a1683526001918290529091208054909181016105df838261092d565b916000526020600020900160005b81546101009190910a600160a060020a0381810219909216888316919091021790915587166000908152600260205260409020805490915060018101610633838261092d565b916000526020600020900160005b8154600160a060020a038089166101009390930a928302920219161790555061066b565b60006000fd5b5b5b5b50505050505050565b6102f961091b565b600160a060020a03331660009081526020819052604090205460ff1615156001141561038f57600160a060020a0382166000908152600160209081526040918290208054835181840281018401909452808452909183018282801561038757602002820191906000526020600020905b8154600160a060020a03168152600190910190602001808311610369575b505050505090505b5b5b919050565b600160a060020a03331660009081526020819052604090205460ff161515600114156103dd57600160a060020a0381166000908152602081905260409020805460ff191660011790555b5b5b50565b61077361091b565b600160a060020a033316600090815260026020908152604091829020805483518184028101840190945280845290918301828280156107db57602002820191906000526020600020905b8154600160a060020a031681526001909101906020018083116107bd575b505050505090505b90565b6000600360006002856002866000604051602001526040518082600160a060020a0316600160a060020a0316606060020a02815260140191505060206040518083038160008661646e5a03f1151561083a57fe5b50506040805180516000602092830181905283519485528483019190915282518484019492939092839003019082908661646e5a03f1151561087857fe5b50506040805151825260208201929092520160002054600160a060020a031690505b92915050565b6108a861091b565b600160a060020a033316600090815260016020908152604091829020805483518184028101840190945280845290918301828280156107db57602002820191906000526020600020905b8154600160a060020a031681526001909101906020018083116107bd575b505050505090505b90565b60408051602081019091526000815290565b81548183558181151161095157600083815260209020610951918101908301610957565b5b505050565b6107e391905b80821115610971576000815560010161095d565b5090565b905600a165627a7a72305820cda77c472066509d2f3601bb2dbed9ab8ec4078f727dd0aa5aabe3c30447637c0029";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<>();
        _addresses.put("87234", "0x67da225e40f0a23e0e67bbed72a33262afc1e4aa");
    }

    protected MainRegistry(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MainRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<DynamicArray<Address>> getAnyRetailerContracts(Address retailer) {
        Function function = new Function("getAnyRetailerContracts", 
                Arrays.<Type>asList(retailer), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> removeAdmin(Address addy) {
        Function function = new Function(
                "removeAdmin", 
                Arrays.<Type>asList(addy), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> placeNewContract(Address _vendor, Address _retailer, Bytes32 _cntName, Address _cntr) {
        Function function = new Function(
                "placeNewContract", 
                Arrays.<Type>asList(_vendor, _retailer, _cntName, _cntr), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<DynamicArray<Address>> getAnyVendorContracts(Address vendor) {
        Function function = new Function("getAnyVendorContracts", 
                Arrays.<Type>asList(vendor), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> addAdmin(Address addy) {
        Function function = new Function(
                "addAdmin", 
                Arrays.<Type>asList(addy), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<DynamicArray<Address>> getRetailerContracts() {
        Function function = new Function("getRetailerContracts", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Address> getContractByName(Bytes32 _name, Address _party) {
        Function function = new Function("getContractByName", 
                Arrays.<Type>asList(_name, _party), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<DynamicArray<Address>> getVendorContracts() {
        Function function = new Function("getVendorContracts", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public static RemoteCall<MainRegistry> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MainRegistry.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<MainRegistry> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MainRegistry.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static MainRegistry load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new MainRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static MainRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new MainRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
