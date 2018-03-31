pragma solidity ^0.4.11;

import "./OSA_tech_token.sol";

contract OSA_VR_m_1 {

    event money_moved(bytes32 osa_hash, bytes32 retailer_hash);

    //kind of dict with kpi level as keys and points of price to be payed
    bytes8[] private kpi_levels;
    bytes8[] private kpi_values;

    mapping(address => bool) private admins;
    uint256 private contract_price_per_day;
    uint256 private osa_price;
    address private vendor;
    address private retailer;
    bytes32 private hash;
    bytes32 private name;
    bytes8  private targetValue;
    bytes32 private metricDatetime;
    address private osa_address;
    OSA_token coin;

    // modifiers
    modifier admin_only {if (admins[msg.sender] == true) _;}
    // constructor
    function OSA_VR_m_1(bytes8[] _kpi_levels, bytes8[] _kpi_values, uint256 _contract_price_per_day, uint256 _osa_price, bytes32 _name, address vendor,address retailer) public {
        require(_kpi_levels.length == _kpi_values.length);
        admins[msg.sender] = true;
        kpi_levels = _kpi_levels;
        kpi_values = _kpi_values;
        contract_price_per_day = _contract_price_per_day;
        osa_price = _osa_price;
        coin = new OSA_token();
        osa_address = msg.sender;
        name = _name;
    }

    function addAdmin(address addy) public admin_only {
        admins[addy] = true;
    }

    function removeAdmin(address addy) public admin_only {
        delete admins[addy];
    }

    function publishHash(bytes32 _hash, bytes8 _targetValue, bytes32 _metricDatetime) public admin_only {
        hash = _hash;
        targetValue = _targetValue;
        metricDatetime = _metricDatetime;
        make_payments();
    }

    function make_payments() private {
        uint256 retailer_payment;
        //found right price for the day
        for (uint i = 0; i < kpi_levels.length; i++) {
            if (targetValue >= kpi_levels[i]) {
                retailer_payment = uint(kpi_values[i]) * contract_price_per_day;
                break;
            }
        }
        // make 2 transactions to osa and retailer
        coin.transfer(retailer, retailer_payment);
        coin.transfer(osa_address, osa_price);
    }

    function subscribe_service(address _vendor, address _retailer, uint256 _days) public {
        uint256 service_price;
        service_price = (_days * contract_price_per_day) + (_days * osa_price);
        //service_price  = contract_price_per_day * _days;
        coin.transfer(this, service_price);
    }

    function selfdestruct() public admin_only {
        admins[msg.sender] = true;
        coin.transfer(msg.sender, coin.balance);
    }

    function getHash() public constant returns (bytes32) {
        return hash;
    }

    function getName() public constant returns (bytes32) {
        return name;
    }

    function getTargetValue() public constant returns (bytes8) {
        return targetValue;
    }

    function getMetricDatetime() public constant returns (bytes32) {
        return metricDatetime;
    }
}