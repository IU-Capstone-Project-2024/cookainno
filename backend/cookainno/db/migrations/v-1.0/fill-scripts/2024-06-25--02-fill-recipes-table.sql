--liquibase formatted sql

--changeset vladislav77777:2
INSERT INTO recipes (id, name, instructions, ingredients, image_url) VALUES
                                                                         (1, 'Паста Карбонара', 'Сварите спагетти. Обжарьте бекон с чесноком. Смешайте яйца и сливки. Смешайте все вместе с пармезаном, посолите и поперчите.', 'спагетти, бекон, яйца, пармезан, чеснок, сливки, соль, перец', 'https://avatars.mds.yandex.net/i?id=e7965b1501330488069e2da9c75c8b97b1b2b3a0-12609276-images-thumbs&n=13'),
                                                                         (2, 'Цезарь Салат', 'Обжарьте курицу. Смешайте салат с пармезаном и гренками. Приготовьте соус из анчоусов, чеснока, майонеза и лимонного сока. Добавьте курицу и перемешайте.', 'куриная грудка, салат ромэн, пармезан, гренки, анчоусы, чеснок, майонез, лимонный сок', 'https://cake-dance.ru/wp-content/uploads/7/3/4/734f4dd223c9ca7b925999b96f4eec0e.jpeg'),
                                                                         (3, 'Борщ', 'Сварите говядину, добавьте овощи и томатную пасту. Варите до готовности. Добавьте уксус, сахар, соль и перец. Подавайте со сметаной.', 'свекла, капуста, картофель, морковь, лук, томатная паста, говядина, уксус, сахар, соль, перец, лавровый лист, сметана', 'https://avatars.mds.yandex.net/i?id=cec3ce8e46c88c466d9e7a632fd742cdc0e3804d-12643871-images-thumbs&n=13'),
                                                                         (4, 'Плов', 'Обжарьте баранину с луком и морковью. Добавьте рис, зиру, соль и перец. Залейте водой и тушите до готовности.', 'рис, баранина, морковь, лук, чеснок, растительное масло, зира, соль, перец', 'https://avatars.mds.yandex.net/i?id=a2da7097562f55d650a37d3f78633fc51d316402-12752609-images-thumbs&n=13'),
                                                                         (5, 'Шашлык', 'Замаринуйте свинину с луком, уксусом, солью и перцем. Жарьте на мангале до готовности.', 'свинина, лук, уксус, соль, перец, растительное масло', 'https://mykaleidoscope.ru/x/uploads/posts/2022-09/1663738063_53-mykaleidoscope-ru-p-shashlik-iz-baranei-koreiki-yeda-oboi-58.jpg'),
                                                                         (6, 'Оливье', 'Сварите картофель, морковь и яйца. Нарежьте все ингредиенты и смешайте с майонезом, солью и перцем.', 'картофель, морковь, зеленый горошек, яйца, колбаса, соленые огурцы, майонез, соль, перец', 'https://www.miloserdie.ru/wp-content/uploads/2023/12/traditional-russian-salad-olivier-scaled.jpg'),
                                                                         (7, 'Греческий салат', 'Нарежьте овощи и сыр. Смешайте с маслинами. Заправьте оливковым маслом, орегано, солью и перцем.', 'помидоры, огурцы, болгарский перец, красный лук, маслины, фета, оливковое масло, орегано, соль, перец', 'https://avatars.mds.yandex.net/i?id=2e8db3aa689f122b8732d49a8965a181ea433059-12605974-images-thumbs&n=13'),
                                                                         (8, 'Пельмени', 'Смешайте фарш с луком, чесноком, солью и перцем. Заверните в тесто и сварите в кипящей воде до готовности.', 'фарш, лук, чеснок, соль, перец, тесто для пельменей, вода', 'https://profrybolov.ru/wp-content/uploads/3/2/b/32b2c2c101eca1640630c31e579e1b04.jpeg'),
                                                                         (9, 'Рыбный суп', 'Сварите рыбу, добавьте овощи и лавровый лист. Варите до готовности. Посолите, поперчите и добавьте зелень.', 'рыба, картофель, морковь, лук, лавровый лист, соль, перец, зелень', 'https://avatars.mds.yandex.net/i?id=453bf9be93dba4a20f1b28974753fe183af3a786-7133642-images-thumbs&n=13'),
                                                                         (10, 'Блины', 'Смешайте все ингредиенты до однородной массы. Жарьте блины на сковороде с растительным маслом до золотистого цвета.', 'мука, молоко, яйца, сахар, соль, растительное масло', 'https://avatars.mds.yandex.net/i?id=5496900e373d3bf070cf9c4a64c1c93d9783eaed-10876252-images-thumbs&n=13');
