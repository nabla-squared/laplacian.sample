
DELETE FROM t_word_list;

DELETE FROM t_word_list_entry;





INSERT INTO t_word_list(
  "name"

) VALUES (

  'word_list_1'

), (

  'word_list_2'

), (

  'word_list_3'

);


INSERT INTO t_word_list_entry(
  "word_list_name",
  "lemma"

) VALUES (

  'word_list_1',
  'hoge'

), (

  'word_list_1',
  'fuga'

), (

  'word_list_1',
  'piyo'

);





INSERT INTO t_word_list_entry(
  "word_list_name",
  "lemma"

) VALUES (

  'word_list_2',
  'hoge'

), (

  'word_list_2',
  'piyo'

);





INSERT INTO t_word_list_entry(
  "word_list_name",
  "lemma"

) VALUES (

  'word_list_3',
  'hoge'

);






