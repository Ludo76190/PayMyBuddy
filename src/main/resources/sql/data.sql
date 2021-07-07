INSERT INTO paymybuddy.user ( firstname, lastname, email, password, balance)
    VALUE ("Ludovic", "Allegaert","allegaertl@gmail.com","$2a$10$/I8vGeLZgB2LwI2/yCcmteTEU6DPehYPuwHT2vHLLVkUSGpOmSmfi", 1000.0),
	("Audrey", "Le Pallec","alpaudrey@gmail.com","$2a$10$/I8vGeLZgB2LwI2/yCcmteTEU6DPehYPuwHT2vHLLVkUSGpOmSmfi", 1000.0),
	("Jeanne", "Allegaert Le Pallec","jalp@gmail.com","$2a$10$/I8vGeLZgB2LwI2/yCcmteTEU6DPehYPuwHT2vHLLVkUSGpOmSmfi", 1000.0),
	("Auguste", "Allegaert Le Pallec","aalp@gmail.com","$2a$10$/I8vGeLZgB2LwI2/yCcmteTEU6DPehYPuwHT2vHLLVkUSGpOmSmfi", 1000.0);

INSERT INTO paymybuddy.friend(user_id,user_friend_id)
    VALUE (1,2),
    (1,3),
    (2,4);

INSERT INTO paymybuddy.bank_account (rib,user_id)
    VALUE ("123",1),
    ("456",2),
    ("789",3),
    ("101112",4);
