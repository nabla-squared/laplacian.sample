
/**
 * The table of word_lists.
 */
CREATE TABLE t_word_list (
    "name" VARCHAR(200) NOT NULL,
    PRIMARY KEY (name)
);


/**
 * The table of word_list_entries.
 */
CREATE TABLE t_word_list_entry (
    "word_list_name" VARCHAR(200) NOT NULL,
    "lemma" VARCHAR(200) NOT NULL,
    PRIMARY KEY (word_list_name, lemma)
);




