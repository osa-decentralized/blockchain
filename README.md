# OSAdc Blockchain

OSA DC is a decentralized, AI-driven blockchain platform
that collects and analyzes data from retailers, manufacturers, consumers,
and open data sources in real-time. You can find it on osadc.io
This repo contains public blockchain solution we are using.

## Getting Started
Compile smart contracts with truffle  
```
cd blockchain\quorum 
truffle compile
```
Generate java wrappers with web3j for needed files like in the example below. 
```
web3j truffle generate --solidityTypes  \contracts\OSA_VR_m_1.json -o blockchain\qproxy\src\main\java -p com.devchain.qproxy
```
Build with sbt

## Prerequisites
Install this components and versions
```
truffle 3.3.2 (with solc 4.11)
web3j 3.2.0
quorum 2.0.2
```

## Architecture
OSAdc uses private blockchain based on Quorum for internal hash and metrics publication. Every publications contains hash, which is also a key in key value database of KPI measures in retailer-vendor contracts. 
Qproxy is a REST interface for quorum blockchain with smart contracts, described in API section.

### qproxy API
Publish a hash for data piece saved in OSA database in the contract for specified vendor-retailer pair
```http
POST /v1/sc/<contract_name>/
{"vendor":  "vendor hash"
	,"retailer": "retailer hash"
	,"target_value": 0.99 
	,"hash_tree_root": "hash"
	,"metric_datetime": "<datetime>"
}
```

Get X statuses for partners
```http
GET /v1/sc/<contract_name>/peer/<partner_hash>/values/365 
{	"values":[["<hash_tree_root>","<target_value>"],["<hash_tree_root>","<target_value>""]
}
```

Create a contract with name and kpi set. 
```http
POST v2/sc
	{"name": "OSA_T_Vendor_Retailer_1",
	"kpi_values": [100,95,90,80,70],
	"kpi_levels": [1,0.9,0.7,0.5,0],
	"contract_price_per_day": 100,
	"osa_price": 5
}
```

Vendor accepts the contract for x days
```http
POST v2/sc/subscribe
{	"days": 100,
	"vendor": "vendor_hash",
	"retailer" : "retailer_hash"
}
```

Post a hash and make a calculations with OSA tech tokens. It's an internal technical token.
```http
POST v2/sc/<name>
{   "target_value": 0.99, 
	"hash_tree_root": "hash",
	"metric_datetime":  "datetime"
}
```
## Built With

* [sbt](https://www.scala-sbt.org/) - The scala build tool
* [truffle](https://github.com/trufflesuite/truffle) - etherium and quorum development platfrom
* [OpenZeppelin](https://github.com/OpenZeppelin/zeppelin-solidity) - Community audited smart contracts

## License

This project is licensed under the GPL License - see the [LICENSE.md](LICENSE.md) file for details

