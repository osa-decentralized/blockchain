pragma solidity ^0.4.11;

contract HashPublisher {

  event HashPublished(bytes32 hash, bytes8 targetValue, bytes32 metricDatetime, uint txDatetime);

  mapping (address => bool) private admins;

  address private vendor;
  address private retailer;
  bytes32 private hash;
  bytes32 private name;
  bytes8  private targetValue;
  bytes32 private metricDatetime;

  // modifiers
  modifier admin_only { if (admins[msg.sender] == true) _; }

  // constructor
  function HashPublisher(address _vendor, address _retailer, bytes32 _name) public {
    admins[msg.sender] = true;
    vendor = _vendor;
    retailer = _retailer;
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
    HashPublished(_hash, _targetValue, _metricDatetime, now);
  }

  function getVendor() public constant returns (address) {
    return vendor;
  }

  function getRetailer() public constant returns (address) {
    return retailer;
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
