pragma solidity ^0.4.11;

contract MainRegistry {

  mapping (address => bool) private admins;

  mapping (address => address[]) private vendorContractAddrs;
  mapping (address => address[]) private retailerContractAddrs;
  mapping (bytes32 => address) private vrContracts;

  // modifiers
  modifier admin_only { if (admins[msg.sender] == true) _; }
  
  // constructor
  function MainRegistry() public {
    admins[msg.sender] = true;
  }

  function addAdmin(address addy) public admin_only {
    admins[addy] = true;
  }

  function removeAdmin(address addy) public admin_only {
    delete admins[addy];
  }
  
  function placeNewContract(address _vendor, address _retailer, bytes32 _cntName, address _cntr) public admin_only {
    address z = address(0);
    bytes32 nvHash = sha256(_cntName, sha256(_vendor));
    bytes32 nrHash = sha256(_cntName, sha256(_retailer));

    if(vrContracts[nvHash] == z && vrContracts[nrHash] == z) {
      vrContracts[nvHash] = _cntr;
      vrContracts[nrHash] = _cntr;
      vendorContractAddrs[_vendor].push(_cntr);
      retailerContractAddrs[_retailer].push(_cntr);
    } else {
      throw;
    }
  }

  function getAnyVendorContracts(address vendor) public constant admin_only returns (address[]) {
    return vendorContractAddrs[vendor];
  }
  
  function getAnyRetailerContracts(address retailer) public constant admin_only returns (address[])  {
    return vendorContractAddrs[retailer];
  }

  // public user functions
  
  function getVendorContracts() public constant returns (address[]) {
    return vendorContractAddrs[msg.sender];
  }

  function getRetailerContracts() public constant returns (address[]) {
    return retailerContractAddrs[msg.sender];
  }

  function getContractByName(bytes32 _name, address _party) public constant returns (address)  {
    return vrContracts[sha256(_name, sha256(_party))];
  }

  function () {
    throw; // throw reverts state to before call
  }

}
