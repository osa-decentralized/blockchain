module.exports = {
  networks: {
    net7: {
      host: "1.1.1.1", //insert your net here
      port: 22000, // was 8545
      network_id: "*", // Match any network id
      gasPrice: 0,
      gas: 4500000
    },
    core2: {
      host: "1.1.1.1", //insert your net here
      port: 22000, // was 8545
      network_id: "*", // Match any network id
      gasPrice: 0,
      gas: 4500000
    }
  }
};
