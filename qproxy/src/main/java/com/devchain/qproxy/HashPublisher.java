package com.devchain.qproxy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Bytes8;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.2.0.
 */
public class HashPublisher extends Contract {
    private static final String BINARY = "6060604052341561000c57fe5b6040516060806104058339810160409081528151602083015191909201515b600160a060020a033381166000908152602081905260409020805460ff191660019081179091558054858316600160a060020a031991821617909155600280549285169290911691909117905560048190555b5050505b610374806100916000396000f3006060604052361561007d5763ffffffff60e060020a6000350416631785f53c811461007f57806317d7de7c1461009d57806370480275146100bf57806372b3b4bf146100dd5780637d4b7411146101025780637e5d9d0d1461012e578063d13319c41461015b578063f80d1ff01461017d578063fc88845f146101a9575bfe5b341561008757fe5b61009b600160a060020a03600435166101cb565b005b34156100a557fe5b6100ad610217565b60408051918252519081900360200190f35b34156100c757fe5b61009b600160a060020a036004351661021e565b005b34156100e557fe5b61009b600435600160c060020a03196024351660443561026d565b005b341561010a57fe5b61011261030d565b60408051600160a060020a039092168252519081900360200190f35b341561013657fe5b61013e61031d565b60408051600160c060020a03199092168252519081900360200190f35b341561016357fe5b6100ad61032a565b60408051918252519081900360200190f35b341561018557fe5b610112610331565b60408051600160a060020a039092168252519081900360200190f35b34156101b157fe5b6100ad610341565b60408051918252519081900360200190f35b600160a060020a03331660009081526020819052604090205460ff1615156001141561021257600160a060020a0381166000908152602081905260409020805460ff191690555b5b5b50565b6004545b90565b600160a060020a03331660009081526020819052604090205460ff1615156001141561021257600160a060020a0381166000908152602081905260409020805460ff191660011790555b5b5b50565b600160a060020a03331660009081526020819052604090205460ff16151560011415610306576003839055600580546001604060020a03191660c060020a8404179055600681905560408051848152600160c060020a03198416602082015280820183905242606082015290517fcd73635b60054dd453bcefb3c3de16d82e7b5960c3840bd01e325ce4f5213ef1916080908290030190a15b5b5b505050565b600154600160a060020a03165b90565b60055460c060020a025b90565b6003545b90565b600254600160a060020a03165b90565b6006545b905600a165627a7a723058207afd4fe4782db5ff4f92edb2b2d53971e87863dc32ed33f78521b7dd62df83ad0029";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<>();
    }

    protected HashPublisher(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected HashPublisher(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<HashPublishedEventResponse> getHashPublishedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("HashPublished", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes8>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<HashPublishedEventResponse> responses = new ArrayList<HashPublishedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            HashPublishedEventResponse typedResponse = new HashPublishedEventResponse();
            typedResponse.hash = (Bytes32) eventValues.getNonIndexedValues().get(0);
            typedResponse.targetValue = (Bytes8) eventValues.getNonIndexedValues().get(1);
            typedResponse.metricDatetime = (Bytes32) eventValues.getNonIndexedValues().get(2);
            typedResponse.txDatetime = (Uint256) eventValues.getNonIndexedValues().get(3);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<HashPublishedEventResponse> hashPublishedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("HashPublished", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes8>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, HashPublishedEventResponse>() {
            @Override
            public HashPublishedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                HashPublishedEventResponse typedResponse = new HashPublishedEventResponse();
                typedResponse.hash = (Bytes32) eventValues.getNonIndexedValues().get(0);
                typedResponse.targetValue = (Bytes8) eventValues.getNonIndexedValues().get(1);
                typedResponse.metricDatetime = (Bytes32) eventValues.getNonIndexedValues().get(2);
                typedResponse.txDatetime = (Uint256) eventValues.getNonIndexedValues().get(3);
                return typedResponse;
            }
        });
    }

    public RemoteCall<TransactionReceipt> removeAdmin(Address addy) {
        Function function = new Function(
                "removeAdmin", 
                Arrays.<Type>asList(addy), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Bytes32> getName() {
        Function function = new Function("getName", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> addAdmin(Address addy) {
        Function function = new Function(
                "addAdmin", 
                Arrays.<Type>asList(addy), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> publishHash(Bytes32 _hash, Bytes8 _targetValue, Bytes32 _metricDatetime) {
        Function function = new Function(
                "publishHash", 
                Arrays.<Type>asList(_hash, _targetValue, _metricDatetime), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Address> getVendor() {
        Function function = new Function("getVendor", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Bytes8> getTargetValue() {
        Function function = new Function("getTargetValue", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes8>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Bytes32> getHash() {
        Function function = new Function("getHash", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Address> getRetailer() {
        Function function = new Function("getRetailer", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Bytes32> getMetricDatetime() {
        Function function = new Function("getMetricDatetime", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public static RemoteCall<HashPublisher> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Address _vendor, Address _retailer, Bytes32 _name) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_vendor, _retailer, _name));
        return deployRemoteCall(HashPublisher.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<HashPublisher> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Address _vendor, Address _retailer, Bytes32 _name) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_vendor, _retailer, _name));
        return deployRemoteCall(HashPublisher.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static HashPublisher load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new HashPublisher(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static HashPublisher load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new HashPublisher(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class HashPublishedEventResponse {
        public Bytes32 hash;

        public Bytes8 targetValue;

        public Bytes32 metricDatetime;

        public Uint256 txDatetime;
    }
}
