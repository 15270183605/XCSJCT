package entity;

public class PayCard {
  private String CardNumber;
  private String CardName;
  private byte[] CardImage;
  private double CardMoney;
public PayCard(String cardNumber, String cardName, byte[] cardImage,
		double cardMoney) {
	super();
	CardNumber = cardNumber;
	CardName = cardName;
	CardImage = cardImage;
	CardMoney = cardMoney;
}
public PayCard() {
	super();
	// TODO Auto-generated constructor stub
}
public String getCardNumber() {
	return CardNumber;
}
public void setCardNumber(String cardNumber) {
	CardNumber = cardNumber;
}
public String getCardName() {
	return CardName;
}
public void setCardName(String cardName) {
	CardName = cardName;
}
public byte[] getCardImage() {
	return CardImage;
}
public void setCardImage(byte[] cardImage) {
	CardImage = cardImage;
}
public double getCardMoney() {
	return CardMoney;
}
public void setCardMoney(double cardMoney) {
	CardMoney = cardMoney;
}
  
}
