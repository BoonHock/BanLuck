package com.fiyepr.banluck.screens.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class GameViewModel : ViewModel() {
	private var gameWonCount: Int = 0
	val wonCount: Int
		get() = gameWonCount

	private var gameLostCount: Int = 0
	val lostCount: Int
		get() = gameLostCount

	private var gameTieCount: Int = 0
	val tieCount: Int
		get() = gameTieCount

	private var gameRunCount: Int = 0
	val runCount: Int
		get() = gameRunCount

	private var playerAceCount: Int = 0
	private var playerCardCount: Int = 0
	private var playerTotalVal: Int = 0

	private var comAceCount: Int = 0
	private var comCardCount: Int = 0
	private var comTotalVal: Int = 0

	// initial players' status after receiving 2 cards
	enum class InitCard(val cardValue: Int) {
		RUN(3),
		BAN_BAN(2),
		BAN_LUCK(1),
		NORMAL(0)
	}

	enum class GameStatus {
		WON,
		LOST,
		TIE,
		RUN,
		ONGOING
	}

	private val _playerCard1 = MutableLiveData<String>()
	val playerCard1: LiveData<String>
		get() = _playerCard1

	private val _playerCard2 = MutableLiveData<String>()
	val playerCard2: LiveData<String>
		get() = _playerCard2

	private val _playerCard3 = MutableLiveData<String>()
	val playerCard3: LiveData<String>
		get() = _playerCard3

	private val _playerCard4 = MutableLiveData<String>()
	val playerCard4: LiveData<String>
		get() = _playerCard4

	private val _playerCard5 = MutableLiveData<String>()
	val playerCard5: LiveData<String>
		get() = _playerCard5

	private val _comCard1 = MutableLiveData<String>()
	val comCard1: LiveData<String>
		get() = _comCard1

	private val _comCard2 = MutableLiveData<String>()
	val comCard2: LiveData<String>
		get() = _comCard2

	private val _comCard3 = MutableLiveData<String>()
	val comCard3: LiveData<String>
		get() = _comCard3

	private val _comCard4 = MutableLiveData<String>()
	val comCard4: LiveData<String>
		get() = _comCard4

	private val _comCard5 = MutableLiveData<String>()
	val comCard5: LiveData<String>
		get() = _comCard5

	private val _playerCardText = MutableLiveData<String>()
	val playerCardText: LiveData<String>
		get() = _playerCardText

	private val _comCardText = MutableLiveData<String>()
	val comCardText: LiveData<String>
		get() = _comCardText

	private val _comText = MutableLiveData<String>()
	val comText: LiveData<String>
		get() = _comText

	private val _playerCardMax = MutableLiveData<Boolean>()
	val playerCardMax: LiveData<Boolean>
		get() = _playerCardMax

	private val _mustHitCard = MutableLiveData<Boolean>()
	val mustHitCard: LiveData<Boolean>
		get() = _mustHitCard

	private val _gameStatus = MutableLiveData<GameStatus>()
	val gameStatus: LiveData<GameStatus>
		get() = _gameStatus

	init {
		resetCards()
	}

	private fun resetCards() {
		playerCardCount = 0
		comCardCount = 0
		playerTotalVal = 0
		comTotalVal = 0

		_playerCard1.value = ""
		_playerCard2.value = ""
		_playerCard3.value = ""
		_playerCard4.value = ""
		_playerCard5.value = ""
		_comCard1.value = ""
		_comCard2.value = ""
		_comCard3.value = ""
		_comCard4.value = ""
		_comCard5.value = ""

		_playerCardText.value = ""
		_comCardText.value = ""

		_gameStatus.value = GameStatus.ONGOING
		_playerCardMax.value = false
		_mustHitCard.value = false
	}

	fun onDeal() {
		Timber.i("onDeal")
		// if player no card means game just started
		if (playerCardCount == 0) {
			// deal player cards
			dealCard(true)
			dealCard(true)

			// deal com cards
			dealCard(false)
			dealCard(false)

			val playerInit = checkInitCard(playerCard1.value, playerCard2.value)
			val comInit = checkInitCard(comCard1.value, comCard2.value)

			if (playerInit != InitCard.NORMAL || comInit != InitCard.NORMAL) {
				gameOver(playerInit, comInit)
			}
		} else {
			dealCard(true)

			if (playerCardCount == 5) {
				gameOver()
			}
		}
	}

	fun onDone() {
		if (playerTotalVal < 16) {
			// cannot done. must be more than or equal to 16
			_mustHitCard.value = true
			return
		}

		// player's done. com's turn
		// get card while cards at hand lesser than 17 or less than 4 cards
		while (comTotalVal < 17 && comCardCount < 5) {
			dealCard(false)
		}

		gameOver()

		Timber.i("Player total: %s", playerTotalVal)
		Timber.i("Player ace count: %s", playerAceCount)
		Timber.i("Com total: %s", comTotalVal)
		Timber.i("Com ace count: %s", comAceCount)
	}

	fun onAgain() {
		resetCards()
	}

	private fun dealCard(is_player: Boolean): String {
		var card: String

		do {
			card = mutableSetOf("c", "d", "h", "s").random() + (1..13).random()

			Timber.i("card: %s", card)
		} while (
			(!_playerCard1.value.isNullOrEmpty() && _playerCard1.value == card) ||
			(!_playerCard2.value.isNullOrEmpty() && _playerCard2.value == card) ||
			(!_playerCard3.value.isNullOrEmpty() && _playerCard3.value == card) ||
			(!_playerCard4.value.isNullOrEmpty() && _playerCard4.value == card) ||
			(!_playerCard5.value.isNullOrEmpty() && _playerCard5.value == card) ||
			(!_comCard1.value.isNullOrEmpty() && _comCard1.value == card) ||
			(!_comCard2.value.isNullOrEmpty() && _comCard2.value == card) ||
			(!_comCard3.value.isNullOrEmpty() && _comCard3.value == card) ||
			(!_comCard4.value.isNullOrEmpty() && _comCard4.value == card) ||
			(!_comCard5.value.isNullOrEmpty() && _comCard5.value == card)
		)

		if (is_player) {
			playerCardCount++
			when (playerCardCount) {
				1 -> _playerCard1.value = card
				2 -> _playerCard2.value = card
				3 -> _playerCard3.value = card
				4 -> _playerCard4.value = card
				5 -> {
					_playerCard5.value = card
					_playerCardMax.value = true
				}
			}
		} else {
			comCardCount++
			when (comCardCount) {
				1 -> _comCard1.value = card
				2 -> _comCard2.value = card
				3 -> _comCard3.value = card
				4 -> _comCard4.value = card
				5 -> _comCard5.value = card
			}
		}
		getCardTotal(is_player)
		return card
	}

	private fun checkInitCard(card1: String?, card2: String?): InitCard {
		var results: InitCard = InitCard.NORMAL // default

		if (card1.isNullOrEmpty() || card2.isNullOrEmpty()) {
			return results
		}

		var card1Val = getCardValue(card1)
		var card2Val = getCardValue(card2)

		card1Val = if (card1Val == 1) 11 else card1Val
		card2Val = if (card2Val == 1) 11 else card2Val

		results = when (card1Val + card2Val) {
			22 -> InitCard.BAN_BAN
			21 -> InitCard.BAN_LUCK
			15 -> InitCard.RUN
			else -> InitCard.NORMAL
		}

		return results
	}

	private fun getCardTotal(is_player: Boolean) {
		if (is_player) {
			playerTotalVal = 0
			playerAceCount = 0

			processCard(playerCard1.value, true)
			processCard(playerCard2.value, true)
			processCard(playerCard3.value, true)
			processCard(playerCard4.value, true)
			processCard(playerCard5.value, true)

			playerTotalVal = getCardTotalWithAce(
				playerTotalVal,
				playerAceCount,
				playerCardCount
			)

			_playerCardText.value = if (playerTotalVal > 21) "KABOOM" else playerTotalVal.toString()
		} else {
			comTotalVal = 0
			comAceCount = 0

			processCard(comCard1.value, false)
			processCard(comCard2.value, false)
			processCard(comCard3.value, false)
			processCard(comCard4.value, false)
			processCard(comCard5.value, false)

			comTotalVal = getCardTotalWithAce(
				comTotalVal,
				comAceCount,
				comCardCount
			)
			_comCardText.value = if (comTotalVal > 21) "KABOOM" else comTotalVal.toString()
		}
	}

	private fun getCardTotalWithAce(totalVal: Int, aceCount: Int, cardCount: Int): Int {
		var results: Int
		var aceMaxVal = 11 // init as max val when 2 cards
		var aceMinVal = 10 // init as min val when 2 cards

		if (cardCount >= 4) {
			results = totalVal + aceCount
		} else {
			if (cardCount == 3) {
				aceMaxVal = 10
				aceMinVal = 1
			}
			var maxAceCount = aceCount + 1

			do {
				maxAceCount--
				results = totalVal + maxAceCount * aceMaxVal + (aceCount - maxAceCount) * aceMinVal
			} while (maxAceCount > 0 && results > 21)
		}

		return results
	}

	private fun getCardValue(cardString: String): Int {
		if (cardString.length < 2) {
			// card string is like, d1 for Diamond Ace or C2 for clover 2
			// should be at least 2 char so if lesser, something's wrong...
			return 0
		}
		return when (val card = cardString.removeRange(0, 1).toInt()) {
			in (10..13) -> 10
			else -> card
		}
	}

	private fun processCard(cardString: String?, is_player: Boolean) {
		if (cardString.isNullOrEmpty()) {
			// if card string null or empty, no need process
			return
		}
		val cardValue = getCardValue(cardString)
		if (is_player) {
			if (cardValue > 1) {
				playerTotalVal += cardValue
			} else if (cardValue == 1) {
				playerAceCount += 1
			}
		} else {
			if (cardValue > 1) {
				comTotalVal += cardValue
			} else if (cardValue == 1) {
				comAceCount += 1
			}
		}
	}

	private fun gameOver(playerInit: InitCard, comInit: InitCard) {
		_gameStatus.value = when {
			playerInit == InitCard.RUN || comInit == InitCard.RUN -> GameStatus.RUN
			playerInit.cardValue > comInit.cardValue -> GameStatus.WON
			playerInit.cardValue < comInit.cardValue -> GameStatus.LOST
			else -> GameStatus.TIE
		}

		_playerCardText.value = when (playerInit) {
			InitCard.RUN -> "RUN"
			InitCard.BAN_BAN -> "BAN BAN"
			InitCard.BAN_LUCK -> "BAN LUCK"
			else -> playerCardText.value
		}
		_comCardText.value = when (comInit) {
			InitCard.RUN -> "RUN"
			InitCard.BAN_BAN -> "BAN BAN"
			InitCard.BAN_LUCK -> "BAN LUCK"
			else -> comCardText.value
		}
		when (gameStatus.value) {
			GameStatus.WON -> gameWonCount++
			GameStatus.LOST -> gameLostCount++
			GameStatus.RUN -> gameRunCount++
			else -> gameTieCount++
		}
	}

	private fun gameOver() {
		_gameStatus.value = when {
			playerCardCount == 5 -> if (playerTotalVal <= 21) GameStatus.WON else GameStatus.LOST
			comCardCount == 5 -> if (comTotalVal <= 21) GameStatus.LOST else GameStatus.WON
			playerTotalVal <= 21 && (comTotalVal > 21 || playerTotalVal > comTotalVal) -> GameStatus.WON
			comTotalVal <= 21 && (playerTotalVal > 21 || comTotalVal > playerTotalVal) -> GameStatus.LOST
			else -> GameStatus.TIE
		}
		when (gameStatus.value) {
			GameStatus.WON -> gameWonCount++
			GameStatus.LOST -> gameLostCount++
			GameStatus.RUN -> gameRunCount++
			else -> gameTieCount++
		}
	}
}