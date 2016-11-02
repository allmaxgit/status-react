var WEB3_UNIT = [
    'kwei/ada',
    'mwei/babbage',
    'gwei/shannon',
    'szabo',
    'finney',
    'ether',
    'kether/grand/einstein',
    'mether',
    'gether',
    'tether',
]

// because web3 doesn't provide params or docs
var WEB3_DOC_MAP = {
    // setProvider     : ['provider'], // TODO
    version: {
        api : { desc: 'The ethereum js api version.' },
        node : { desc: 'The client/node version.' },
        network : { desc: 'The network protocol version.' },
        ethereum : { desc: 'The ethereum protocol version.' },
        whisper : { desc: 'The whisper protocol version.' },
    },
    isConnected : { desc: 'Check if a connection to a node exists.' },
    net : {
        listening : { desc: 'Is node actively listening for network connections?' },
        peerCount : { desc: 'Returns the number of connected peers' }
    },
    sha3            : {
        desc: 'Returns the Keccak-256 SHA3 of the given data.',
        args: ['string', 'options']
    },
    toHex           : {
        desc: 'Converts any value into HEX',
        args: ['stringOrNumber']
    },
    toAscii         : {
        desc: 'Converts a HEX string into a ASCII string.',
        args: ['hexString']
    },
    fromAscii       : {
        desc: 'Converts any ASCII string to a HEX string.',
        args: ['textString', '[padding]']
    },
    toDecimal       : {
        desc: 'Converts a HEX string to its number representation.',
        args: ['hexString']
    },
    fromDecimal     : {
        desc: 'Converts a number or number string to its HEX representation.',
        args: ['number']
    },
    fromWei         : {
        desc: 'Converts a number of wei into an ethereum unit',
        args: ['numberStringOrBigNumber', 'unit']
    },
    toWei           : {
        desc: 'Converts an ethereum unit into wei',
        args: ['numberStringOrBigNumber', 'unit']
    },
    toBigNumber     : {
        desc: 'Converts a given number into a BigNumber instance',
        args: ['numberOrHexString']
    },
    isAddress       : ['hexString'], // TODO not in docs

    eth : {
        defaultBlock : { desc: 'This default block' },
        syncing : { desc: 'Returns the either a sync object, when the node is syncing or false.' },
        coinbase : { desc: 'Returns the coinbase address' },
        gasPrice : { desc: 'Returns the current gas price. The gas price is determined by the x latest blocks median gas price' },
        accounts : { desc: 'Returns a list of accounts the node controls' },
        blockNumber : { desc: 'Returns the current block number' },

        getBalance                      : {
            desc: 'Get the balance of an address at a given block.',
            args: ['address', '[defaultBlock]', '[callback]']
        },
        getStorageAt                    : {
            desc: 'Get the storage at a specific position of an address.',
            args: ['address', 'position', '[defaultBlock]', '[callback]']
        },
        getCode                         : {
            desc: 'Get the code at a specific address.',
            args: ['address', '[defaultBlock]', '[callback]']
        },
        getBlock                        : {
            desc: 'Returns a block matching the block number or block hash.',
            args: ['hashOrBlockNumber', '[returnTransactionObjects]', '[callback]']
        },
        getBlockTransactionCount        : {
            desc: 'Returns the number of transaction in a given block.',
            args: ['hashOrBlockNumber', '[callback]']
        },
        getUncle                        : {
            desc: 'Returns a blocks uncle by a given uncle index position',
            args:['hashOrBlockNumber', 'uncleNumber', '[returnTransactionObjects]']
        },
        getBlockUncleCount              : {
            desc: '',
            args: ['hashOrBlockNumber'], // TODO missing from docs
        },
        getTransaction                  : {
            desc: 'Returns a transaction matching the given transaction hash.',
            args: ['hash', '[callback]']
        },
        getTransactionFromBlock         : {
            desc: 'Returns a transaction based on a block hash or number and the transactions index position.',
            args: ['hashOrBlockNumber', 'indexNumber', '[callback]']
        },
        getTransactionReceipt           : {
            desc: 'Returns the receipt of a transaction by transaction hash.',
            args: ['hash', '[callback]']
        },
        getTransactionCount             : {
            desc: 'Get the numbers of transactions sent from this address.',
            args: ['address', '[defaultBlock]', '[callback]']
        },
        sendTransaction                 : {
            desc: 'Sends a transaction to the network.',
            args: [{
                    from        : 'address',
                    to          : '[address]',
                    value       : '[numberStringOrBigNumber]',
                    gas         : '[numberStringOrBigNumber]',
                    gasPrice    : '[numberStringOrBigNumber]',
                    data        : '[hexString]',
                    nonce       : '[number]'
                  }, '[callback]']
        },
        sendRawTransaction              : {
            desc: 'Sends an already signed transaction.',
            args: ['hexString', '[callback]']
        },
        sign                            : {
            desc: 'Signs data from a specific account. This account needs to be unlocked.',
            args: ['address', 'hexString', '[callback]']
        },
        call                            : {
            desc: 'Executes a message call transaction, which is directly executed in the VM of the node, but never mined into the blockchain.',
            args: ['object', '[numberStringOrBigNumber]', '[callback]']
        },
        estimateGas                     : {
            desc: 'Executes a message call or transaction, which is directly executed in the VM of the node, but never mined into the blockchain and returns the amount of the gas used.',
            args: ['object'] // TODO same as sendTransaction, everything optional
        },

        // TODO filters
        // watch                           : ['callback'],
        // stopWatching                    : ['callback'],
        contract                        : {
            desc: 'Creates a contract object for a solidity contract, which can be used to initiate contracts on an address.',
            args: ['abiArray']
        },
        getCompilers                    : {
            desc: 'Gets a list of available compilers.',
            args: ['[callback]']
        },

        compile : { // TODO we should auto hide these depending on output from getCompilers
            lll                         : {
                desc: 'Compiles LLL source code.',
                args: ['string']
            },
            solidity                    : {
                desc: 'Compiles solidity source code',
                args: ['string'],
            },
            serpent                     : {
                desc: 'Compiles serpent source code',
                args: ['string']
            }
        }
    },

    db: {
        putString     : {
            desc: 'Store a string in the local leveldb database. (db, key, value)',
            args: ['string', 'string', 'string']
        },
        getString     : {
            desc: 'Retrieve a string from the local leveldb database. (db, key)',
            args: ['string', 'string']
        },
        putHex        : {
            desc: 'Store binary data in the local leveldb database. (db, key, value)',
            args: ['string', 'string', 'hexString']
        },
        getHex        : {
            desc: 'Retrieve binary data from the local leveldb database. (db, key)',
            args: ['string', 'string']
        }
    }
};

status.command({
    name: "web3",
    description: "Access the web3 object",
    color: "#7099e6",
    params: [{
        name: "query",
        type: status.types.TEXT
    }]
});


var phones = [
    {
        number: "89171111111",
        description: "Number format 1"
    },
    {
        number: "89371111111",
        description: "Number format 1"
    },
    {
        number: "+79171111111",
        description: "Number format 2"
    },
    {
        number: "9171111111",
        description: "Number format 3"
    }
];

function suggestionsContainerStyle(suggestionsCount) {
    return {
        marginVertical: 1,
        marginHorizontal: 0,
        height: Math.min(150, (56 * suggestionsCount)),
        backgroundColor: "white",
        borderRadius: 5
    };
}

var suggestionContainerStyle = {
    paddingLeft: 16,
    backgroundColor: "white"
};

var suggestionSubContainerStyle = {
    height: 56,
    borderBottomWidth: 1,
    borderBottomColor: "#0000001f"
};

var valueStyle = {
    marginTop: 9,
    fontSize: 14,
    fontFamily: "font",
    color: "#000000de"
};

var descriptionStyle = {
    marginTop: 1.5,
    fontSize: 14,
    fontFamily: "font",
    color: "#838c93de"
};

function startsWith(str1, str2) {
    // String.startsWith(...) doesn't work in otto
    return str1.lastIndexOf(str2, 0) == 0 && str1 != str2;
}

function phoneSuggestions(params) {
    var ph, suggestions;
    if (!params.phone || params.phone == "") {
        ph = phones;
    } else {
        ph = phones.filter(function (phone) {
            return startsWith(phone.number, params.phone);
        });
    }

    if (ph.length == 0) {
        return;
    }

    suggestions = ph.map(function (phone) {
        return status.components.touchable(
            {onPress: [status.events.SET_VALUE, phone.number]},
            status.components.view(suggestionContainerStyle,
                [status.components.view(suggestionSubContainerStyle,
                    [
                        status.components.text(
                            {style: valueStyle},
                            phone.number
                        ),
                        status.components.text(
                            {style: descriptionStyle},
                            phone.description
                        )
                    ])])
        );
    });

    var view = status.components.scrollView(
        suggestionsContainerStyle(ph.length),
        suggestions
    );

    return {markup: view};
}

var phoneConfig = {
    name: "phone",
    icon: "phone_white",
    title: "Send Phone Number",
    description: "Find friends using your number",
    color: "#5bb2a2",
    params: [{
        name: "phone",
        type: status.types.PHONE,
        suggestions: phoneSuggestions,
        placeholder: "Phone number"
    }]
};
status.response(phoneConfig);
status.command(phoneConfig);


status.command({
    name: "help",
    title: "Help",
    description: "Request help from Console",
    color: "#7099e6",
    params: [{
        name: "query",
        type: status.types.TEXT
    }]
});

status.response({
    name: "confirmation-code",
    color: "#7099e6",
    description: "Confirmation code",
    params: [{
        name: "code",
        type: status.types.NUMBER
    }],
    validator: function (params) {
        if (!/^[\d]{4}$/.test(params.code)) {
            var error = status.components.validationMessage(
                "Confirmation code",
                "Wrong format"
            );

            return {errors: [error]}
        }
    }
});

status.response({
    name: "password",
    color: "#7099e6",
    description: "Password",
    icon: "lock_white",
    params: [{
        name: "password",
        type: status.types.PASSWORD,
        placeholder: "Type your password"
    }, {
        name: "password-confirmation",
        type: status.types.PASSWORD,
        placeholder: "Please re-enter password to confirm"
    }],
    validator: function (params, context) {
        var errorMessages = [];
        var currentParameter = context["current-parameter"];

        if (
            currentParameter == "password" &&
            params.password.length < 6
        ) {
            errorMessages.push("Password should be not less then 6 symbols.");
        }

        if (currentParameter == "password-confirmation" &&
            params.password != params["password-confirmation"]) {
            errorMessages.push("Password confirmation doesn't match password.");
        }

        if (errorMessages.length) {
            var errors = [];
            for (var idx in errorMessages) {
                errors.push(
                    status.components.validationMessage(
                        "Password",
                        errorMessages[idx]
                    )
                );
            }

            return {errors: errors};
        }

        return {params: params, context: context};
    },
    preview: function (params, context) {
        var style = {
            marginTop: 5,
            marginHorizontal: 0,
            fontSize: 14,
            color: "black"
        };

        if (context.platform == "ios") {
            style.fontSize = 8;
            style.marginTop = 10;
            style.marginBottom = 2;
            style.letterSpacing = 1;
        }

        return status.components.text({style: style}, "●●●●●●●●●●");
    }
});
