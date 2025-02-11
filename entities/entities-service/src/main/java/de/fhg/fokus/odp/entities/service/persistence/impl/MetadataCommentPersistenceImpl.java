/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package de.fhg.fokus.odp.entities.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import de.fhg.fokus.odp.entities.exception.NoSuchMetadataCommentException;
import de.fhg.fokus.odp.entities.model.MetadataComment;
import de.fhg.fokus.odp.entities.model.MetadataCommentTable;
import de.fhg.fokus.odp.entities.model.impl.MetadataCommentImpl;
import de.fhg.fokus.odp.entities.model.impl.MetadataCommentModelImpl;
import de.fhg.fokus.odp.entities.service.persistence.MetadataCommentPersistence;
import de.fhg.fokus.odp.entities.service.persistence.MetadataCommentUtil;
import de.fhg.fokus.odp.entities.service.persistence.impl.constants.entitiesPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the metadata comment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = MetadataCommentPersistence.class)
public class MetadataCommentPersistenceImpl
	extends BasePersistenceImpl<MetadataComment>
	implements MetadataCommentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MetadataCommentUtil</code> to access the metadata comment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MetadataCommentImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the metadata comments where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching metadata comments
	 */
	@Override
	public List<MetadataComment> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the metadata comments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MetadataCommentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of metadata comments
	 * @param end the upper bound of the range of metadata comments (not inclusive)
	 * @return the range of matching metadata comments
	 */
	@Override
	public List<MetadataComment> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the metadata comments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MetadataCommentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of metadata comments
	 * @param end the upper bound of the range of metadata comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching metadata comments
	 */
	@Override
	public List<MetadataComment> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MetadataComment> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the metadata comments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MetadataCommentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of metadata comments
	 * @param end the upper bound of the range of metadata comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching metadata comments
	 */
	@Override
	public List<MetadataComment> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MetadataComment> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<MetadataComment> list = null;

		if (useFinderCache) {
			list = (List<MetadataComment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MetadataComment metadataComment : list) {
					if (!uuid.equals(metadataComment.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_METADATACOMMENT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MetadataCommentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<MetadataComment>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first metadata comment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching metadata comment
	 * @throws NoSuchMetadataCommentException if a matching metadata comment could not be found
	 */
	@Override
	public MetadataComment findByUuid_First(
			String uuid, OrderByComparator<MetadataComment> orderByComparator)
		throws NoSuchMetadataCommentException {

		MetadataComment metadataComment = fetchByUuid_First(
			uuid, orderByComparator);

		if (metadataComment != null) {
			return metadataComment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchMetadataCommentException(sb.toString());
	}

	/**
	 * Returns the first metadata comment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching metadata comment, or <code>null</code> if a matching metadata comment could not be found
	 */
	@Override
	public MetadataComment fetchByUuid_First(
		String uuid, OrderByComparator<MetadataComment> orderByComparator) {

		List<MetadataComment> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last metadata comment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching metadata comment
	 * @throws NoSuchMetadataCommentException if a matching metadata comment could not be found
	 */
	@Override
	public MetadataComment findByUuid_Last(
			String uuid, OrderByComparator<MetadataComment> orderByComparator)
		throws NoSuchMetadataCommentException {

		MetadataComment metadataComment = fetchByUuid_Last(
			uuid, orderByComparator);

		if (metadataComment != null) {
			return metadataComment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchMetadataCommentException(sb.toString());
	}

	/**
	 * Returns the last metadata comment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching metadata comment, or <code>null</code> if a matching metadata comment could not be found
	 */
	@Override
	public MetadataComment fetchByUuid_Last(
		String uuid, OrderByComparator<MetadataComment> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<MetadataComment> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the metadata comments before and after the current metadata comment in the ordered set where uuid = &#63;.
	 *
	 * @param _id the primary key of the current metadata comment
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next metadata comment
	 * @throws NoSuchMetadataCommentException if a metadata comment with the primary key could not be found
	 */
	@Override
	public MetadataComment[] findByUuid_PrevAndNext(
			long _id, String uuid,
			OrderByComparator<MetadataComment> orderByComparator)
		throws NoSuchMetadataCommentException {

		uuid = Objects.toString(uuid, "");

		MetadataComment metadataComment = findByPrimaryKey(_id);

		Session session = null;

		try {
			session = openSession();

			MetadataComment[] array = new MetadataCommentImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, metadataComment, uuid, orderByComparator, true);

			array[1] = metadataComment;

			array[2] = getByUuid_PrevAndNext(
				session, metadataComment, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MetadataComment getByUuid_PrevAndNext(
		Session session, MetadataComment metadataComment, String uuid,
		OrderByComparator<MetadataComment> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_METADATACOMMENT_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(MetadataCommentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						metadataComment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MetadataComment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the metadata comments where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (MetadataComment metadataComment :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(metadataComment);
		}
	}

	/**
	 * Returns the number of metadata comments where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching metadata comments
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_METADATACOMMENT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"metadataComment.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(metadataComment.uuid IS NULL OR metadataComment.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByuserLiferayId;
	private FinderPath _finderPathWithoutPaginationFindByuserLiferayId;
	private FinderPath _finderPathCountByuserLiferayId;

	/**
	 * Returns all the metadata comments where userLiferayId = &#63;.
	 *
	 * @param userLiferayId the user liferay ID
	 * @return the matching metadata comments
	 */
	@Override
	public List<MetadataComment> findByuserLiferayId(long userLiferayId) {
		return findByuserLiferayId(
			userLiferayId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the metadata comments where userLiferayId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MetadataCommentModelImpl</code>.
	 * </p>
	 *
	 * @param userLiferayId the user liferay ID
	 * @param start the lower bound of the range of metadata comments
	 * @param end the upper bound of the range of metadata comments (not inclusive)
	 * @return the range of matching metadata comments
	 */
	@Override
	public List<MetadataComment> findByuserLiferayId(
		long userLiferayId, int start, int end) {

		return findByuserLiferayId(userLiferayId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the metadata comments where userLiferayId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MetadataCommentModelImpl</code>.
	 * </p>
	 *
	 * @param userLiferayId the user liferay ID
	 * @param start the lower bound of the range of metadata comments
	 * @param end the upper bound of the range of metadata comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching metadata comments
	 */
	@Override
	public List<MetadataComment> findByuserLiferayId(
		long userLiferayId, int start, int end,
		OrderByComparator<MetadataComment> orderByComparator) {

		return findByuserLiferayId(
			userLiferayId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the metadata comments where userLiferayId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MetadataCommentModelImpl</code>.
	 * </p>
	 *
	 * @param userLiferayId the user liferay ID
	 * @param start the lower bound of the range of metadata comments
	 * @param end the upper bound of the range of metadata comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching metadata comments
	 */
	@Override
	public List<MetadataComment> findByuserLiferayId(
		long userLiferayId, int start, int end,
		OrderByComparator<MetadataComment> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByuserLiferayId;
				finderArgs = new Object[] {userLiferayId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByuserLiferayId;
			finderArgs = new Object[] {
				userLiferayId, start, end, orderByComparator
			};
		}

		List<MetadataComment> list = null;

		if (useFinderCache) {
			list = (List<MetadataComment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MetadataComment metadataComment : list) {
					if (userLiferayId != metadataComment.getUserLiferayId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_METADATACOMMENT_WHERE);

			sb.append(_FINDER_COLUMN_USERLIFERAYID_USERLIFERAYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MetadataCommentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userLiferayId);

				list = (List<MetadataComment>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first metadata comment in the ordered set where userLiferayId = &#63;.
	 *
	 * @param userLiferayId the user liferay ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching metadata comment
	 * @throws NoSuchMetadataCommentException if a matching metadata comment could not be found
	 */
	@Override
	public MetadataComment findByuserLiferayId_First(
			long userLiferayId,
			OrderByComparator<MetadataComment> orderByComparator)
		throws NoSuchMetadataCommentException {

		MetadataComment metadataComment = fetchByuserLiferayId_First(
			userLiferayId, orderByComparator);

		if (metadataComment != null) {
			return metadataComment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userLiferayId=");
		sb.append(userLiferayId);

		sb.append("}");

		throw new NoSuchMetadataCommentException(sb.toString());
	}

	/**
	 * Returns the first metadata comment in the ordered set where userLiferayId = &#63;.
	 *
	 * @param userLiferayId the user liferay ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching metadata comment, or <code>null</code> if a matching metadata comment could not be found
	 */
	@Override
	public MetadataComment fetchByuserLiferayId_First(
		long userLiferayId,
		OrderByComparator<MetadataComment> orderByComparator) {

		List<MetadataComment> list = findByuserLiferayId(
			userLiferayId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last metadata comment in the ordered set where userLiferayId = &#63;.
	 *
	 * @param userLiferayId the user liferay ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching metadata comment
	 * @throws NoSuchMetadataCommentException if a matching metadata comment could not be found
	 */
	@Override
	public MetadataComment findByuserLiferayId_Last(
			long userLiferayId,
			OrderByComparator<MetadataComment> orderByComparator)
		throws NoSuchMetadataCommentException {

		MetadataComment metadataComment = fetchByuserLiferayId_Last(
			userLiferayId, orderByComparator);

		if (metadataComment != null) {
			return metadataComment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userLiferayId=");
		sb.append(userLiferayId);

		sb.append("}");

		throw new NoSuchMetadataCommentException(sb.toString());
	}

	/**
	 * Returns the last metadata comment in the ordered set where userLiferayId = &#63;.
	 *
	 * @param userLiferayId the user liferay ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching metadata comment, or <code>null</code> if a matching metadata comment could not be found
	 */
	@Override
	public MetadataComment fetchByuserLiferayId_Last(
		long userLiferayId,
		OrderByComparator<MetadataComment> orderByComparator) {

		int count = countByuserLiferayId(userLiferayId);

		if (count == 0) {
			return null;
		}

		List<MetadataComment> list = findByuserLiferayId(
			userLiferayId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the metadata comments before and after the current metadata comment in the ordered set where userLiferayId = &#63;.
	 *
	 * @param _id the primary key of the current metadata comment
	 * @param userLiferayId the user liferay ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next metadata comment
	 * @throws NoSuchMetadataCommentException if a metadata comment with the primary key could not be found
	 */
	@Override
	public MetadataComment[] findByuserLiferayId_PrevAndNext(
			long _id, long userLiferayId,
			OrderByComparator<MetadataComment> orderByComparator)
		throws NoSuchMetadataCommentException {

		MetadataComment metadataComment = findByPrimaryKey(_id);

		Session session = null;

		try {
			session = openSession();

			MetadataComment[] array = new MetadataCommentImpl[3];

			array[0] = getByuserLiferayId_PrevAndNext(
				session, metadataComment, userLiferayId, orderByComparator,
				true);

			array[1] = metadataComment;

			array[2] = getByuserLiferayId_PrevAndNext(
				session, metadataComment, userLiferayId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MetadataComment getByuserLiferayId_PrevAndNext(
		Session session, MetadataComment metadataComment, long userLiferayId,
		OrderByComparator<MetadataComment> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_METADATACOMMENT_WHERE);

		sb.append(_FINDER_COLUMN_USERLIFERAYID_USERLIFERAYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(MetadataCommentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userLiferayId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						metadataComment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MetadataComment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the metadata comments where userLiferayId = &#63; from the database.
	 *
	 * @param userLiferayId the user liferay ID
	 */
	@Override
	public void removeByuserLiferayId(long userLiferayId) {
		for (MetadataComment metadataComment :
				findByuserLiferayId(
					userLiferayId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(metadataComment);
		}
	}

	/**
	 * Returns the number of metadata comments where userLiferayId = &#63;.
	 *
	 * @param userLiferayId the user liferay ID
	 * @return the number of matching metadata comments
	 */
	@Override
	public int countByuserLiferayId(long userLiferayId) {
		FinderPath finderPath = _finderPathCountByuserLiferayId;

		Object[] finderArgs = new Object[] {userLiferayId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_METADATACOMMENT_WHERE);

			sb.append(_FINDER_COLUMN_USERLIFERAYID_USERLIFERAYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userLiferayId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERLIFERAYID_USERLIFERAYID_2 =
		"metadataComment.userLiferayId = ?";

	private FinderPath _finderPathWithPaginationFindBymetadataName;
	private FinderPath _finderPathWithoutPaginationFindBymetadataName;
	private FinderPath _finderPathCountBymetadataName;

	/**
	 * Returns all the metadata comments where metadataName = &#63;.
	 *
	 * @param metadataName the metadata name
	 * @return the matching metadata comments
	 */
	@Override
	public List<MetadataComment> findBymetadataName(String metadataName) {
		return findBymetadataName(
			metadataName, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the metadata comments where metadataName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MetadataCommentModelImpl</code>.
	 * </p>
	 *
	 * @param metadataName the metadata name
	 * @param start the lower bound of the range of metadata comments
	 * @param end the upper bound of the range of metadata comments (not inclusive)
	 * @return the range of matching metadata comments
	 */
	@Override
	public List<MetadataComment> findBymetadataName(
		String metadataName, int start, int end) {

		return findBymetadataName(metadataName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the metadata comments where metadataName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MetadataCommentModelImpl</code>.
	 * </p>
	 *
	 * @param metadataName the metadata name
	 * @param start the lower bound of the range of metadata comments
	 * @param end the upper bound of the range of metadata comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching metadata comments
	 */
	@Override
	public List<MetadataComment> findBymetadataName(
		String metadataName, int start, int end,
		OrderByComparator<MetadataComment> orderByComparator) {

		return findBymetadataName(
			metadataName, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the metadata comments where metadataName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MetadataCommentModelImpl</code>.
	 * </p>
	 *
	 * @param metadataName the metadata name
	 * @param start the lower bound of the range of metadata comments
	 * @param end the upper bound of the range of metadata comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching metadata comments
	 */
	@Override
	public List<MetadataComment> findBymetadataName(
		String metadataName, int start, int end,
		OrderByComparator<MetadataComment> orderByComparator,
		boolean useFinderCache) {

		metadataName = Objects.toString(metadataName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindBymetadataName;
				finderArgs = new Object[] {metadataName};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindBymetadataName;
			finderArgs = new Object[] {
				metadataName, start, end, orderByComparator
			};
		}

		List<MetadataComment> list = null;

		if (useFinderCache) {
			list = (List<MetadataComment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MetadataComment metadataComment : list) {
					if (!metadataName.equals(
							metadataComment.getMetadataName())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_METADATACOMMENT_WHERE);

			boolean bindMetadataName = false;

			if (metadataName.isEmpty()) {
				sb.append(_FINDER_COLUMN_METADATANAME_METADATANAME_3);
			}
			else {
				bindMetadataName = true;

				sb.append(_FINDER_COLUMN_METADATANAME_METADATANAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MetadataCommentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindMetadataName) {
					queryPos.add(metadataName);
				}

				list = (List<MetadataComment>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first metadata comment in the ordered set where metadataName = &#63;.
	 *
	 * @param metadataName the metadata name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching metadata comment
	 * @throws NoSuchMetadataCommentException if a matching metadata comment could not be found
	 */
	@Override
	public MetadataComment findBymetadataName_First(
			String metadataName,
			OrderByComparator<MetadataComment> orderByComparator)
		throws NoSuchMetadataCommentException {

		MetadataComment metadataComment = fetchBymetadataName_First(
			metadataName, orderByComparator);

		if (metadataComment != null) {
			return metadataComment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("metadataName=");
		sb.append(metadataName);

		sb.append("}");

		throw new NoSuchMetadataCommentException(sb.toString());
	}

	/**
	 * Returns the first metadata comment in the ordered set where metadataName = &#63;.
	 *
	 * @param metadataName the metadata name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching metadata comment, or <code>null</code> if a matching metadata comment could not be found
	 */
	@Override
	public MetadataComment fetchBymetadataName_First(
		String metadataName,
		OrderByComparator<MetadataComment> orderByComparator) {

		List<MetadataComment> list = findBymetadataName(
			metadataName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last metadata comment in the ordered set where metadataName = &#63;.
	 *
	 * @param metadataName the metadata name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching metadata comment
	 * @throws NoSuchMetadataCommentException if a matching metadata comment could not be found
	 */
	@Override
	public MetadataComment findBymetadataName_Last(
			String metadataName,
			OrderByComparator<MetadataComment> orderByComparator)
		throws NoSuchMetadataCommentException {

		MetadataComment metadataComment = fetchBymetadataName_Last(
			metadataName, orderByComparator);

		if (metadataComment != null) {
			return metadataComment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("metadataName=");
		sb.append(metadataName);

		sb.append("}");

		throw new NoSuchMetadataCommentException(sb.toString());
	}

	/**
	 * Returns the last metadata comment in the ordered set where metadataName = &#63;.
	 *
	 * @param metadataName the metadata name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching metadata comment, or <code>null</code> if a matching metadata comment could not be found
	 */
	@Override
	public MetadataComment fetchBymetadataName_Last(
		String metadataName,
		OrderByComparator<MetadataComment> orderByComparator) {

		int count = countBymetadataName(metadataName);

		if (count == 0) {
			return null;
		}

		List<MetadataComment> list = findBymetadataName(
			metadataName, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the metadata comments before and after the current metadata comment in the ordered set where metadataName = &#63;.
	 *
	 * @param _id the primary key of the current metadata comment
	 * @param metadataName the metadata name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next metadata comment
	 * @throws NoSuchMetadataCommentException if a metadata comment with the primary key could not be found
	 */
	@Override
	public MetadataComment[] findBymetadataName_PrevAndNext(
			long _id, String metadataName,
			OrderByComparator<MetadataComment> orderByComparator)
		throws NoSuchMetadataCommentException {

		metadataName = Objects.toString(metadataName, "");

		MetadataComment metadataComment = findByPrimaryKey(_id);

		Session session = null;

		try {
			session = openSession();

			MetadataComment[] array = new MetadataCommentImpl[3];

			array[0] = getBymetadataName_PrevAndNext(
				session, metadataComment, metadataName, orderByComparator,
				true);

			array[1] = metadataComment;

			array[2] = getBymetadataName_PrevAndNext(
				session, metadataComment, metadataName, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MetadataComment getBymetadataName_PrevAndNext(
		Session session, MetadataComment metadataComment, String metadataName,
		OrderByComparator<MetadataComment> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_METADATACOMMENT_WHERE);

		boolean bindMetadataName = false;

		if (metadataName.isEmpty()) {
			sb.append(_FINDER_COLUMN_METADATANAME_METADATANAME_3);
		}
		else {
			bindMetadataName = true;

			sb.append(_FINDER_COLUMN_METADATANAME_METADATANAME_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(MetadataCommentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindMetadataName) {
			queryPos.add(metadataName);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						metadataComment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MetadataComment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the metadata comments where metadataName = &#63; from the database.
	 *
	 * @param metadataName the metadata name
	 */
	@Override
	public void removeBymetadataName(String metadataName) {
		for (MetadataComment metadataComment :
				findBymetadataName(
					metadataName, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(metadataComment);
		}
	}

	/**
	 * Returns the number of metadata comments where metadataName = &#63;.
	 *
	 * @param metadataName the metadata name
	 * @return the number of matching metadata comments
	 */
	@Override
	public int countBymetadataName(String metadataName) {
		metadataName = Objects.toString(metadataName, "");

		FinderPath finderPath = _finderPathCountBymetadataName;

		Object[] finderArgs = new Object[] {metadataName};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_METADATACOMMENT_WHERE);

			boolean bindMetadataName = false;

			if (metadataName.isEmpty()) {
				sb.append(_FINDER_COLUMN_METADATANAME_METADATANAME_3);
			}
			else {
				bindMetadataName = true;

				sb.append(_FINDER_COLUMN_METADATANAME_METADATANAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindMetadataName) {
					queryPos.add(metadataName);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_METADATANAME_METADATANAME_2 =
		"metadataComment.metadataName = ?";

	private static final String _FINDER_COLUMN_METADATANAME_METADATANAME_3 =
		"(metadataComment.metadataName IS NULL OR metadataComment.metadataName = '')";

	public MetadataCommentPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("text", "text_");

		setDBColumnNames(dbColumnNames);

		setModelClass(MetadataComment.class);

		setModelImplClass(MetadataCommentImpl.class);
		setModelPKClass(long.class);

		setTable(MetadataCommentTable.INSTANCE);
	}

	/**
	 * Caches the metadata comment in the entity cache if it is enabled.
	 *
	 * @param metadataComment the metadata comment
	 */
	@Override
	public void cacheResult(MetadataComment metadataComment) {
		entityCache.putResult(
			MetadataCommentImpl.class, metadataComment.getPrimaryKey(),
			metadataComment);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the metadata comments in the entity cache if it is enabled.
	 *
	 * @param metadataComments the metadata comments
	 */
	@Override
	public void cacheResult(List<MetadataComment> metadataComments) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (metadataComments.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (MetadataComment metadataComment : metadataComments) {
			if (entityCache.getResult(
					MetadataCommentImpl.class,
					metadataComment.getPrimaryKey()) == null) {

				cacheResult(metadataComment);
			}
		}
	}

	/**
	 * Clears the cache for all metadata comments.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MetadataCommentImpl.class);

		finderCache.clearCache(MetadataCommentImpl.class);
	}

	/**
	 * Clears the cache for the metadata comment.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MetadataComment metadataComment) {
		entityCache.removeResult(MetadataCommentImpl.class, metadataComment);
	}

	@Override
	public void clearCache(List<MetadataComment> metadataComments) {
		for (MetadataComment metadataComment : metadataComments) {
			entityCache.removeResult(
				MetadataCommentImpl.class, metadataComment);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(MetadataCommentImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(MetadataCommentImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new metadata comment with the primary key. Does not add the metadata comment to the database.
	 *
	 * @param _id the primary key for the new metadata comment
	 * @return the new metadata comment
	 */
	@Override
	public MetadataComment create(long _id) {
		MetadataComment metadataComment = new MetadataCommentImpl();

		metadataComment.setNew(true);
		metadataComment.setPrimaryKey(_id);

		String uuid = PortalUUIDUtil.generate();

		metadataComment.setUuid(uuid);

		return metadataComment;
	}

	/**
	 * Removes the metadata comment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param _id the primary key of the metadata comment
	 * @return the metadata comment that was removed
	 * @throws NoSuchMetadataCommentException if a metadata comment with the primary key could not be found
	 */
	@Override
	public MetadataComment remove(long _id)
		throws NoSuchMetadataCommentException {

		return remove((Serializable)_id);
	}

	/**
	 * Removes the metadata comment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the metadata comment
	 * @return the metadata comment that was removed
	 * @throws NoSuchMetadataCommentException if a metadata comment with the primary key could not be found
	 */
	@Override
	public MetadataComment remove(Serializable primaryKey)
		throws NoSuchMetadataCommentException {

		Session session = null;

		try {
			session = openSession();

			MetadataComment metadataComment = (MetadataComment)session.get(
				MetadataCommentImpl.class, primaryKey);

			if (metadataComment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMetadataCommentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(metadataComment);
		}
		catch (NoSuchMetadataCommentException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected MetadataComment removeImpl(MetadataComment metadataComment) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(metadataComment)) {
				metadataComment = (MetadataComment)session.get(
					MetadataCommentImpl.class,
					metadataComment.getPrimaryKeyObj());
			}

			if (metadataComment != null) {
				session.delete(metadataComment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (metadataComment != null) {
			clearCache(metadataComment);
		}

		return metadataComment;
	}

	@Override
	public MetadataComment updateImpl(MetadataComment metadataComment) {
		boolean isNew = metadataComment.isNew();

		if (!(metadataComment instanceof MetadataCommentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(metadataComment.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					metadataComment);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in metadataComment proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MetadataComment implementation " +
					metadataComment.getClass());
		}

		MetadataCommentModelImpl metadataCommentModelImpl =
			(MetadataCommentModelImpl)metadataComment;

		if (Validator.isNull(metadataComment.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			metadataComment.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(metadataComment);
			}
			else {
				metadataComment = (MetadataComment)session.merge(
					metadataComment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			MetadataCommentImpl.class, metadataCommentModelImpl, false, true);

		if (isNew) {
			metadataComment.setNew(false);
		}

		metadataComment.resetOriginalValues();

		return metadataComment;
	}

	/**
	 * Returns the metadata comment with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the metadata comment
	 * @return the metadata comment
	 * @throws NoSuchMetadataCommentException if a metadata comment with the primary key could not be found
	 */
	@Override
	public MetadataComment findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMetadataCommentException {

		MetadataComment metadataComment = fetchByPrimaryKey(primaryKey);

		if (metadataComment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMetadataCommentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return metadataComment;
	}

	/**
	 * Returns the metadata comment with the primary key or throws a <code>NoSuchMetadataCommentException</code> if it could not be found.
	 *
	 * @param _id the primary key of the metadata comment
	 * @return the metadata comment
	 * @throws NoSuchMetadataCommentException if a metadata comment with the primary key could not be found
	 */
	@Override
	public MetadataComment findByPrimaryKey(long _id)
		throws NoSuchMetadataCommentException {

		return findByPrimaryKey((Serializable)_id);
	}

	/**
	 * Returns the metadata comment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param _id the primary key of the metadata comment
	 * @return the metadata comment, or <code>null</code> if a metadata comment with the primary key could not be found
	 */
	@Override
	public MetadataComment fetchByPrimaryKey(long _id) {
		return fetchByPrimaryKey((Serializable)_id);
	}

	/**
	 * Returns all the metadata comments.
	 *
	 * @return the metadata comments
	 */
	@Override
	public List<MetadataComment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the metadata comments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MetadataCommentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of metadata comments
	 * @param end the upper bound of the range of metadata comments (not inclusive)
	 * @return the range of metadata comments
	 */
	@Override
	public List<MetadataComment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the metadata comments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MetadataCommentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of metadata comments
	 * @param end the upper bound of the range of metadata comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of metadata comments
	 */
	@Override
	public List<MetadataComment> findAll(
		int start, int end,
		OrderByComparator<MetadataComment> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the metadata comments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MetadataCommentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of metadata comments
	 * @param end the upper bound of the range of metadata comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of metadata comments
	 */
	@Override
	public List<MetadataComment> findAll(
		int start, int end,
		OrderByComparator<MetadataComment> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<MetadataComment> list = null;

		if (useFinderCache) {
			list = (List<MetadataComment>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_METADATACOMMENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_METADATACOMMENT;

				sql = sql.concat(MetadataCommentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<MetadataComment>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the metadata comments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MetadataComment metadataComment : findAll()) {
			remove(metadataComment);
		}
	}

	/**
	 * Returns the number of metadata comments.
	 *
	 * @return the number of metadata comments
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_METADATACOMMENT);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "_id";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_METADATACOMMENT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return MetadataCommentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the metadata comment persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathWithPaginationFindByuserLiferayId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByuserLiferayId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userLiferayId"}, true);

		_finderPathWithoutPaginationFindByuserLiferayId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByuserLiferayId",
			new String[] {Long.class.getName()}, new String[] {"userLiferayId"},
			true);

		_finderPathCountByuserLiferayId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByuserLiferayId",
			new String[] {Long.class.getName()}, new String[] {"userLiferayId"},
			false);

		_finderPathWithPaginationFindBymetadataName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBymetadataName",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"metadataName"}, true);

		_finderPathWithoutPaginationFindBymetadataName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBymetadataName",
			new String[] {String.class.getName()},
			new String[] {"metadataName"}, true);

		_finderPathCountBymetadataName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBymetadataName",
			new String[] {String.class.getName()},
			new String[] {"metadataName"}, false);

		MetadataCommentUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		MetadataCommentUtil.setPersistence(null);

		entityCache.removeCache(MetadataCommentImpl.class.getName());
	}

	@Override
	@Reference(
		target = entitiesPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = entitiesPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = entitiesPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_METADATACOMMENT =
		"SELECT metadataComment FROM MetadataComment metadataComment";

	private static final String _SQL_SELECT_METADATACOMMENT_WHERE =
		"SELECT metadataComment FROM MetadataComment metadataComment WHERE ";

	private static final String _SQL_COUNT_METADATACOMMENT =
		"SELECT COUNT(metadataComment) FROM MetadataComment metadataComment";

	private static final String _SQL_COUNT_METADATACOMMENT_WHERE =
		"SELECT COUNT(metadataComment) FROM MetadataComment metadataComment WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "metadataComment.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No MetadataComment exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No MetadataComment exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MetadataCommentPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "text"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}