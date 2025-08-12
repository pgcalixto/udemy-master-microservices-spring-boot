package com.pgcalixto.calixtobank.cards.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.pgcalixto.calixtobank.cards.constants.CardConstants;
import com.pgcalixto.calixtobank.cards.dto.CardDto;
import com.pgcalixto.calixtobank.cards.entity.Card;
import com.pgcalixto.calixtobank.cards.exception.CardAlreadyExistsException;
import com.pgcalixto.calixtobank.cards.exception.ResourceNotFoundException;
import com.pgcalixto.calixtobank.cards.mapper.CardMapper;
import com.pgcalixto.calixtobank.cards.repository.CardRepository;
import com.pgcalixto.calixtobank.cards.service.CardService;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {

    private static final Random RANDOM = new Random();

    private CardMapper cardMapper;

    private CardRepository cardRepository;

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public void createCard(String mobileNumber) {

        final Optional<Card> optionalCard = cardRepository.findByMobileNumber(mobileNumber);

        if (optionalCard.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }

        cardRepository.save(createNewCard(mobileNumber));
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private Card createNewCard(String mobileNumber) {

        final long randomCardNumber = 100000000000L + RANDOM.nextInt(900000000);

        final Card newCard = new Card();
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);

        return newCard;
    }

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return Card Details based on a given mobileNumber
     */
    @Override
    public CardDto fetchCard(String mobileNumber) {

        final Card card = cardRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );

        return cardMapper.mapToCardDto(card);
    }

    /**
     *
     * @param cardDto - CardDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public boolean updateCard(CardDto cardDto) {

        final Card card = cardRepository
                .findByCardNumber(cardDto.getCardNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Card", "CardNumber", cardDto.getCardNumber()));

        cardMapper.updateCard(cardDto, card);

        cardRepository.save(card);

        return  true;
    }

    /**
     * @param mobileNumber - Input MobileNumber
     * @return boolean indicating if the delete of card details is successful or not
     */
    @Override
    public boolean deleteCard(String mobileNumber) {

        final Card card = cardRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));

        cardRepository.deleteById(card.getCardId());

        return true;
    }

}
