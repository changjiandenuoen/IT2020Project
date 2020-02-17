let model;
let category;
let mainDeck;
let communalPile;
let players = [];
let fadeTime = 350;
let fadeInDelay = 600;


// Reset game data
resetGame();


function textFadeInOut(element, toText) {
	if(element.html() == toText) return;
	element.fadeTo(fadeTime, 0, function() {
		$(this).html(toText).fadeTo(fadeTime, 1);
	});
}


// Methods to update date from server
function updateModel() {
	let xhr = createCORSRequest('GET', "/toptrumps/model");
	xhr.onload = function() {
		model = JSON.parse(xhr.responseText);
	};
	xhr.send();
}
function updateCategory() {
	let xhr = createCORSRequest('GET', "/toptrumps/category");
	xhr.onload = function() {
		category = JSON.parse(xhr.responseText);
	};
	xhr.send();
}
function updateMainDeck() {
	let xhr = createCORSRequest('GET', "/toptrumps/deck");
	xhr.onload = function() {
		mainDeck = JSON.parse(xhr.responseText);
	};
	xhr.send();
}
function updateCommunalPile() {
	let xhr = createCORSRequest('GET', "/toptrumps/communalPile");
	xhr.onload = function() {
		communalPile = JSON.parse(xhr.responseText);
	};
	xhr.send();
}
function updatePlayers() {
	for(let i = 0; i < model.numPlayers; i++) {
		let xhr = createCORSRequest('GET', "/toptrumps/player?Index="+i);
		xhr.onload = function() {
			players[i] = JSON.parse(xhr.responseText);
		};
		xhr.send();
	}
}

function resetGame() {
	let xhr = createCORSRequest('GET', "/toptrumps/resetGame");
	xhr.send();
	updateModel();
	updateCategory();
	updateMainDeck();
	updateCommunalPile();
	updatePlayers();
}

function startGame() {
	let xhr = createCORSRequest('GET', "/toptrumps/startGame");
	xhr.send();
	updateModel();
	updateCommunalPile();
	updatePlayers();
}

function setCurrAttrIndex(currAttrIndex) {
	let xhr = createCORSRequest('PUT', "/toptrumps/setCurrAttrIndex");
	xhr.send(currAttrIndex);
	updateModel();
}

function setAIHostCurrAttr() {
	let xhr = createCORSRequest('GET', "/toptrumps/aiHostCurrAttr");
	xhr.send();
	updateModel();
}

function battle() {
	let xhr = createCORSRequest('GET', "/toptrumps/battle");
	xhr.send();
	updateModel();
	updateCommunalPile();
	updatePlayers();
}

function skipAutoBattle() {
	let xhr = createCORSRequest('GET', "/toptrumps/autoBattle");
	xhr.send();
	updateModel();
	updateCommunalPile();
	updatePlayers();
}

function whoIsWinner() {
	let xhr = createCORSRequest('GET', "/toptrumps/whoIsWinner");
	xhr.send();
	updateModel();
}


// Getters
function getModel() {
	return model;
}
function getCategory() {
	return category;
}
function getMainDeck() {
	return mainDeck;
}
function getCommunalPile() {
	return communalPile;
}
function getPlayer(index) {
	return players[index];
}
function getHostName() {
	return players[model.hostIndex].name;
}
function getYourCard() {
	return players[0].deck.topCard.name;
}
function getCurrAttrName() {
	return category.attributes[model.currAttributeIndex].name;
}
function getFadeTime() {
	return fadeTime;
}
function getFadeInDelay() {
	return fadeInDelay;
}



