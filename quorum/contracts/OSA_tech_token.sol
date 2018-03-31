pragma solidity ^0.4.11;

import "./StandardToken.sol";


contract OSA_token is StandardToken {
    string public name;
    uint8 public decimals;
    string public symbol;
    string public version = 'OSATC_0.3';

    function OSA_ttoken() {
        balances[msg.sender] = 1000000000000000;
        //totalSupply = 1000000000;
        name = "OSA_token";
        decimals = 6;
        symbol = "OSATT";
    }
}

