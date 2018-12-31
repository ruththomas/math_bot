import Ws from './Ws'

import _ from 'underscore'
import { AdminEvent } from './AdminEvent'
import { LevelStats } from './LevelStats'
import $store from '../store/store'

export default class AdminControl extends Ws {
  actions = {
    userCount: 'user-count',
    activeUserCount: 'active-user-count',
    signups: 'signups',
    loginsLastXDays: 'logins-last-x-days',
    levelStats: 'level-stats',
    maxLevel: 'max-level',
    getEvents: 'events-get',
    postEvents: 'events-post',
    putEvent: 'events-put',
    delEvent: 'events-delete'
  }

  userCount = 0
  activeUserCount = 0
  lastXDaysLoginCount = 0
  userAccountSignups = []

  levelStats = {'0': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '0'}, '00': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00'}, '000': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000'}, '0000': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '0000'}, '00000': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00000'}, '00001': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00001'}, '000010': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000010'}, '000011': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000011'}, '000012': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000012'}, '00002': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00002'}, '00003': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00003'}, '00004': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00004'}, '00005': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00005'}, '00006': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00006'}, '00007': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00007'}, '00008': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00008'}, '00009': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00009'}, '0001': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '0001'}, '00010': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00010'}, '00011': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00011'}, '000110': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000110'}, '000111': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000111'}, '000112': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000112'}, '00012': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00012'}, '00013': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00013'}, '00014': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00014'}, '00015': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00015'}, '00016': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00016'}, '00017': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00017'}, '00018': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00018'}, '00019': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00019'}, '0002': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '0002'}, '00020': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00020'}, '00021': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00021'}, '00022': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00022'}, '00023': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00023'}, '00024': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00024'}, '00025': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00025'}, '00026': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00026'}, '00027': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00027'}, '00028': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00028'}, '0003': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '0003'}, '00030': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00030'}, '00031': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00031'}, '000310': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000310'}, '000311': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000311'}, '000312': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000312'}, '000313': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000313'}, '000314': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000314'}, '00032': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00032'}, '00033': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00033'}, '00034': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00034'}, '00035': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00035'}, '00036': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00036'}, '00037': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00037'}, '00038': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00038'}, '00039': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00039'}, '0004': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '0004'}, '00040': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00040'}, '00041': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00041'}, '000410': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000410'}, '000411': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000411'}, '000412': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000412'}, '000413': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000413'}, '000414': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000414'}, '000415': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '000415'}, '00042': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00042'}, '00043': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00043'}, '00044': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00044'}, '00045': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00045'}, '00046': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00046'}, '00047': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00047'}, '00048': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00048'}, '00049': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00049'}, '001': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001'}, '0010': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '0010'}, '00100': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00100'}, '00101': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00101'}, '001010': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001010'}, '001011': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001011'}, '001012': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001012'}, '001013': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001013'}, '001014': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001014'}, '001015': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001015'}, '001016': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001016'}, '001017': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001017'}, '00102': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00102'}, '00103': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00103'}, '00104': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00104'}, '00105': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00105'}, '00106': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00106'}, '00107': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00107'}, '00108': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00108'}, '00109': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00109'}, '0011': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '0011'}, '00110': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00110'}, '00111': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00111'}, '001110': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001110'}, '001111': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001111'}, '001112': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001112'}, '001113': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001113'}, '001114': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001114'}, '001115': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001115'}, '001116': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001116'}, '001117': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001117'}, '00112': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00112'}, '00113': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00113'}, '00114': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00114'}, '00115': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00115'}, '00116': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00116'}, '00117': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00117'}, '00118': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00118'}, '00119': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00119'}, '0012': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '0012'}, '00120': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00120'}, '00121': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00121'}, '001210': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001210'}, '001211': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001211'}, '001212': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001212'}, '001213': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001213'}, '001214': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001214'}, '001215': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001215'}, '001216': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001216'}, '001217': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001217'}, '00122': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00122'}, '00123': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00123'}, '00124': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00124'}, '00125': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00125'}, '00126': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00126'}, '00127': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00127'}, '00128': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00128'}, '00129': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00129'}, '0013': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '0013'}, '00130': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00130'}, '00131': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00131'}, '001310': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001310'}, '001311': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001311'}, '001312': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001312'}, '001313': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001313'}, '001314': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001314'}, '001315': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001315'}, '001316': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001316'}, '001317': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001317'}, '00132': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00132'}, '00133': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00133'}, '00134': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00134'}, '00135': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00135'}, '00136': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00136'}, '00137': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00137'}, '00138': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00138'}, '00139': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00139'}, '0014': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '0014'}, '00140': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00140'}, '00141': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00141'}, '001410': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001410'}, '001411': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001411'}, '001412': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001412'}, '001413': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001413'}, '001414': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001414'}, '001415': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001415'}, '001416': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001416'}, '001417': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001417'}, '00142': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00142'}, '00143': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00143'}, '00144': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00144'}, '00145': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00145'}, '00146': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00146'}, '00147': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00147'}, '00148': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00148'}, '00149': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00149'}, '0015': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '0015'}, '00150': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00150'}, '00151': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00151'}, '001510': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001510'}, '001511': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001511'}, '001512': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001512'}, '001513': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001513'}, '001514': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001514'}, '001515': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001515'}, '001516': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001516'}, '001517': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001517'}, '00152': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00152'}, '00153': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00153'}, '00154': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00154'}, '00155': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00155'}, '00156': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00156'}, '00157': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00157'}, '00158': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00158'}, '00159': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00159'}, '0016': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '0016'}, '00160': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00160'}, '00161': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00161'}, '001610': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001610'}, '001611': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001611'}, '001612': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001612'}, '001613': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001613'}, '001614': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001614'}, '001615': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001615'}, '001616': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001616'}, '001617': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '001617'}, '00162': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00162'}, '00163': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00163'}, '00164': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00164'}, '00165': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00165'}, '00166': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00166'}, '00167': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00167'}, '00168': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00168'}, '00169': {'_id': '', 'timesPlayed': 0, 'timesPlayedAvg': 0, 'timesPlayedMax': 0, 'wins': 0, 'winsAvg': 0, 'winsMax': 0, 'id': '00169'}}

  maxLevel = {'0': {'_id': '0', 'count': 0}, '001017': {'_id': '001017', 'count': 0}, '001610': {'_id': '001610', 'count': 0}, '00031': {'_id': '00031', 'count': 0}, '00143': {'_id': '00143', 'count': 0}, '00165': {'_id': '00165', 'count': 0}, '00149': {'_id': '00149', 'count': 0}, '001517': {'_id': '001517', 'count': 0}, '00009': {'_id': '00009', 'count': 0}, '001210': {'_id': '001210', 'count': 0}, '00106': {'_id': '00106', 'count': 0}, '001': {'_id': '001', 'count': 0}, '00154': {'_id': '00154', 'count': 0}, '00042': {'_id': '00042', 'count': 0}, '001616': {'_id': '001616', 'count': 0}, '000410': {'_id': '000410', 'count': 0}, '00138': {'_id': '00138', 'count': 0}, '001515': {'_id': '001515', 'count': 0}, '001014': {'_id': '001014', 'count': 0}, '00116': {'_id': '00116', 'count': 0}, '001314': {'_id': '001314', 'count': 0}, '00020': {'_id': '00020', 'count': 0}, '001215': {'_id': '001215', 'count': 0}, '0001': {'_id': '0001', 'count': 0}, '00127': {'_id': '00127', 'count': 0}, '00110': {'_id': '00110', 'count': 0}, '000313': {'_id': '000313', 'count': 0}, '001115': {'_id': '001115', 'count': 0}, '00121': {'_id': '00121', 'count': 0}, '00004': {'_id': '00004', 'count': 0}, '0016': {'_id': '0016', 'count': 0}, '00019': {'_id': '00019', 'count': 0}, '001613': {'_id': '001613', 'count': 0}, '001310': {'_id': '001310', 'count': 0}, '00049': {'_id': '00049', 'count': 0}, '00140': {'_id': '00140', 'count': 0}, '00111': {'_id': '00111', 'count': 0}, '0000': {'_id': '0000', 'count': 0}, '001415': {'_id': '001415', 'count': 0}, '00144': {'_id': '00144', 'count': 0}, '00008': {'_id': '00008', 'count': 0}, '00101': {'_id': '00101', 'count': 0}, '001112': {'_id': '001112', 'count': 0}, '00045': {'_id': '00045', 'count': 0}, '001015': {'_id': '001015', 'count': 0}, '00015': {'_id': '00015', 'count': 0}, '00133': {'_id': '00133', 'count': 0}, '00160': {'_id': '00160', 'count': 0}, '00038': {'_id': '00038', 'count': 0}, '001110': {'_id': '001110', 'count': 0}, '000414': {'_id': '000414', 'count': 0}, '0011': {'_id': '0011', 'count': 0}, '00122': {'_id': '00122', 'count': 0}, '00026': {'_id': '00026', 'count': 0}, '00131': {'_id': '00131', 'count': 0}, '0013': {'_id': '0013', 'count': 0}, '001211': {'_id': '001211', 'count': 0}, '001516': {'_id': '001516', 'count': 0}, '00032': {'_id': '00032', 'count': 0}, '00151': {'_id': '00151', 'count': 0}, '001317': {'_id': '001317', 'count': 0}, '00142': {'_id': '00142', 'count': 0}, '000415': {'_id': '000415', 'count': 0}, '00166': {'_id': '00166', 'count': 0}, '00043': {'_id': '00043', 'count': 0}, '00048': {'_id': '00048', 'count': 0}, '000314': {'_id': '000314', 'count': 0}, '00109': {'_id': '00109', 'count': 0}, '00120': {'_id': '00120', 'count': 0}, '00005': {'_id': '00005', 'count': 0}, '00102': {'_id': '00102', 'count': 0}, '001617': {'_id': '001617', 'count': 0}, '000411': {'_id': '000411', 'count': 0}, '0002': {'_id': '0002', 'count': 0}, '00139': {'_id': '00139', 'count': 0}, '00107': {'_id': '00107', 'count': 0}, '001512': {'_id': '001512', 'count': 0}, '00037': {'_id': '00037', 'count': 0}, '00162': {'_id': '00162', 'count': 0}, '001116': {'_id': '001116', 'count': 0}, '001313': {'_id': '001313', 'count': 0}, '000310': {'_id': '000310', 'count': 0}, '00112': {'_id': '00112', 'count': 0}, '00123': {'_id': '00123', 'count': 0}, '00103': {'_id': '00103', 'count': 0}, '001416': {'_id': '001416', 'count': 0}, '0012': {'_id': '0012', 'count': 0}, '001214': {'_id': '001214', 'count': 0}, '001012': {'_id': '001012', 'count': 0}, '00161': {'_id': '00161', 'count': 0}, '00156': {'_id': '00156', 'count': 0}, '00132': {'_id': '00132', 'count': 0}, '00145': {'_id': '00145', 'count': 0}, '0010': {'_id': '0010', 'count': 0}, '00117': {'_id': '00117', 'count': 0}, '00128': {'_id': '00128', 'count': 0}, '00027': {'_id': '00027', 'count': 0}, '00150': {'_id': '00150', 'count': 0}, '00167': {'_id': '00167', 'count': 0}, '00016': {'_id': '00016', 'count': 0}, '00134': {'_id': '00134', 'count': 0}, '001411': {'_id': '001411', 'count': 0}, '00152': {'_id': '00152', 'count': 0}, '00044': {'_id': '00044', 'count': 0}, '001113': {'_id': '001113', 'count': 0}, '00033': {'_id': '00033', 'count': 0}, '0014': {'_id': '0014', 'count': 0}, '00022': {'_id': '00022', 'count': 0}, '00163': {'_id': '00163', 'count': 0}, '00119': {'_id': '00119', 'count': 0}, '000412': {'_id': '000412', 'count': 0}, '00169': {'_id': '00169', 'count': 0}, '000311': {'_id': '000311', 'count': 0}, '00000': {'_id': '00000', 'count': 0}, '001513': {'_id': '001513', 'count': 0}, '00006': {'_id': '00006', 'count': 0}, '00158': {'_id': '00158', 'count': 0}, '000010': {'_id': '000010', 'count': 0}, '001217': {'_id': '001217', 'count': 0}, '00047': {'_id': '00047', 'count': 0}, '00011': {'_id': '00011', 'count': 0}, '00104': {'_id': '00104', 'count': 0}, '0003': {'_id': '0003', 'count': 0}, '00036': {'_id': '00036', 'count': 0}, '00113': {'_id': '00113', 'count': 0}, '000112': {'_id': '000112', 'count': 0}, '00124': {'_id': '00124', 'count': 0}, '00001': {'_id': '00001', 'count': 0}, '00023': {'_id': '00023', 'count': 0}, '00159': {'_id': '00159', 'count': 0}, '001013': {'_id': '001013', 'count': 0}, '001213': {'_id': '001213', 'count': 0}, '00135': {'_id': '00135', 'count': 0}, '001312': {'_id': '001312', 'count': 0}, '001011': {'_id': '001011', 'count': 0}, '00012': {'_id': '00012', 'count': 0}, '001510': {'_id': '001510', 'count': 0}, '00040': {'_id': '00040', 'count': 0}, '00129': {'_id': '00129', 'count': 0}, '000': {'_id': '000', 'count': 0}, '00028': {'_id': '00028', 'count': 0}, '00146': {'_id': '00146', 'count': 0}, '001612': {'_id': '001612', 'count': 0}, '00118': {'_id': '00118', 'count': 0}, '001316': {'_id': '001316', 'count': 0}, '001412': {'_id': '001412', 'count': 0}, '00017': {'_id': '00017', 'count': 0}, '00155': {'_id': '00155', 'count': 0}, '00148': {'_id': '00148', 'count': 0}, '00014': {'_id': '00014', 'count': 0}, '00': {'_id': '00', 'count': 0}, '00137': {'_id': '00137', 'count': 0}, '00105': {'_id': '00105', 'count': 0}, '000110': {'_id': '000110', 'count': 0}, '001514': {'_id': '001514', 'count': 0}, '00153': {'_id': '00153', 'count': 0}, '00041': {'_id': '00041', 'count': 0}, '00034': {'_id': '00034', 'count': 0}, '001413': {'_id': '001413', 'count': 0}, '00164': {'_id': '00164', 'count': 0}, '001216': {'_id': '001216', 'count': 0}, '00100': {'_id': '00100', 'count': 0}, '0015': {'_id': '0015', 'count': 0}, '001615': {'_id': '001615', 'count': 0}, '00010': {'_id': '00010', 'count': 0}, '0004': {'_id': '0004', 'count': 0}, '001417': {'_id': '001417', 'count': 0}, '00030': {'_id': '00030', 'count': 0}, '001315': {'_id': '001315', 'count': 0}, '00157': {'_id': '00157', 'count': 0}, '000413': {'_id': '000413', 'count': 0}, '00168': {'_id': '00168', 'count': 0}, '001114': {'_id': '001114', 'count': 0}, '000312': {'_id': '000312', 'count': 0}, '00021': {'_id': '00021', 'count': 0}, '00115': {'_id': '00115', 'count': 0}, '000011': {'_id': '000011', 'count': 0}, '00125': {'_id': '00125', 'count': 0}, '00039': {'_id': '00039', 'count': 0}, '001410': {'_id': '001410', 'count': 0}, '00136': {'_id': '00136', 'count': 0}, '00024': {'_id': '00024', 'count': 0}, '001117': {'_id': '001117', 'count': 0}, '001311': {'_id': '001311', 'count': 0}, '00013': {'_id': '00013', 'count': 0}, '00141': {'_id': '00141', 'count': 0}, '00002': {'_id': '00002', 'count': 0}, '00130': {'_id': '00130', 'count': 0}, '000012': {'_id': '000012', 'count': 0}, '00108': {'_id': '00108', 'count': 0}, '00114': {'_id': '00114', 'count': 0}, '00147': {'_id': '00147', 'count': 0}, '000111': {'_id': '000111', 'count': 0}, '00126': {'_id': '00126', 'count': 0}, '00003': {'_id': '00003', 'count': 0}, '001614': {'_id': '001614', 'count': 0}, '001414': {'_id': '001414', 'count': 0}, '00035': {'_id': '00035', 'count': 0}, '00025': {'_id': '00025', 'count': 0}, '001016': {'_id': '001016', 'count': 0}, '00018': {'_id': '00018', 'count': 0}, '00046': {'_id': '00046', 'count': 0}, '001010': {'_id': '001010', 'count': 0}, '001212': {'_id': '001212', 'count': 0}, '001511': {'_id': '001511', 'count': 0}, '00007': {'_id': '00007', 'count': 0}, '001611': {'_id': '001611', 'count': 0}, '001111': {'_id': '001111', 'count': 0}}
  events = []
  event = null

  constructor () {
    super('admin')
  }

  getEvents () {
    this._send(JSON.stringify({action: this.actions.getEvents}))
  }

  postEvent (event) {
    this._send(JSON.stringify({
      action: this.actions.postEvents,
      newEvent: AdminControl._mapEventToRequest(event)}))
  }

  putEvent (event) {
    this._send(JSON.stringify({
      action: this.actions.putEvent,
      event: AdminControl._mapEventToRequest(event)}))
  }

  delEvent (event) {
    this._send(JSON.stringify({
      action: this.actions.delEvent,
      event: AdminControl._mapEventToRequest(event)}))
  }

  static _mapEventToRequest (event) {
    return Object.assign({}, event, {

      date: new Date(event.date).getTime()
    })
  }

  /*

    Attempt to open socket, if non-admin user opening fails, and need to handle
    @returns Promise(string | Error)

   */

  openSocket () {
    return new Promise((resolve, reject) => {
      try {
        this._getWsPath(this.connection, path => {
          this._ws = new WebSocket(path)
          this._ws.onopen = () => {
            console.log(`${this.connection.toUpperCase()} WS OPEN`)
            this._wsOnMessage(this.handleSocketResponse.bind(this))
            resolve('success')
          }
          this._ws.onerror = (err) => {
            console.error(`${this.connection.toUpperCase()} WS FAILED`, err)

            const messageBuilder = {
              type: 'warn',
              msg: 'Websocket error'
            }
            $store.dispatch('addMessage', messageBuilder)

            reject(err)
          }
          this._ws.onclose = () => {
            console.log(`${this.connection.toUpperCase()} WS CLOSED`)

            const messageBuilder = {
              type: 'warn',
              msg: 'Websocket closed'
            }
            $store.dispatch('addMessage', messageBuilder)
          }
        })
      } catch (e) {
        console.error('error')
        reject(e)
      }
    })
  }

  /*

    fetch user count from socket request
    @param cb function
    @returns callback w/ {status: string = success, userCount: string = 1}
   */

  getActiveUserCount () {
    this._send(JSON.stringify({ action: this.actions.activeUserCount })) // { status: 'success',userCount: "1"}
  }

  getUserCount () {
    this._send(JSON.stringify({ action: this.actions.userCount })) // { status: 'success',userCount: "1"}
  }

  getDailySignups () {
    this._send(JSON.stringify({ action: this.actions.signups }))
  }

  getLoginsLastXDays (days = 7) {
    this._send(JSON.stringify({
      action: this.actions.loginsLastXDays,
      days
    }))
  }

  getLevelStats (_id) {
    this._send(JSON.stringify({ action: this.actions.levelStats, level: _id }))
  }

  getMaxLevelStats () {
    this._send(JSON.stringify({ action: this.actions.maxLevel }))
  }

  handleSocketResponse (result) {
    const {
      status = 'failure',
      activeUserCount = null,
      lastXDaysLoginCount = null,
      events = null,
      message = null,
      event = null,
      userAccountSignups = null,
      userCount = null,
      levelStats = null,
      maxLevel = null
    } = result

    if (status !== 'success') {
      console.error('socket error', result)
      return false
    }

    if (message) {
      const messageBuilder = {
        type: 'info',
        msg: message
      }

      const regex = new RegExp('success|remove', 'gi')

      // todo switch based on message
      if (message.match(regex)) {
        this.getEvents()
      }
      $store.dispatch('addMessage', messageBuilder)
    }

    if (userCount != null) {
      this.userCount = parseInt(userCount)
    }

    if (activeUserCount != null) {
      this.activeUserCount = parseInt(activeUserCount)
    }

    if (lastXDaysLoginCount != null) {
      this.lastXDaysLoginCount = parseInt(lastXDaysLoginCount)
    }

    if (userAccountSignups) {
      const signups = userAccountSignups.map(item => {
        const {_id: {month, day, year}} = item
        return Object.assign({}, item, {
          date: new Date(year, month, day)
        })
      })
      this.userAccountSignups = _.sortBy(signups, 'date')
    }

    if (maxLevel) {
      maxLevel.forEach(level => {
        Object.assign(this.maxLevel, {
          [level._id]: level
        })
      })
    }

    if (events) {
      this.events = events.map(evt => new AdminEvent(evt))
    }

    if (event) {
      this.event = event

      this.getEvents()
    }

    if (levelStats) {
      const levels = levelStats.map(l => new LevelStats(l))

      levels.forEach(level => {
        Object.assign(this.levelStats, {
          [level.id]: level
        })
      })
    }
  }
}
